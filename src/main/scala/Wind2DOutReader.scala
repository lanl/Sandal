/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import squants.motion.VelocityConversions._

class Wind2D(val velocity: RDD[(GridPoint2, Velocity2)],
             val length: Distance2,
             val origin: Position2,
             val dims: GridPoint2,
             val toGridPoint: Position2 => GridPoint2,
             val toWorldPosition: GridPoint2 => Position2,
             val enclosing: RDD[(GridPoint2, (GridPoint2, Int))],
             val dxdy: Distance2)

object Wind2DOutReader {
  def apply(filename: String, config: Wind2DConfig)
           (implicit sc: SparkContext): Wind2D = {
    val lines = sc.textFile(filename).persist()

    // read the wind field
    val field = lines.zipWithIndex().flatMap { case (line, row) =>
      val numbers = line.split( """\s+""")
      numbers.sliding(2, 2).zipWithIndex.map { case (uv, col) =>
        (GridPoint2(row, col.toLong), Velocity2(uv.head.toDouble.mps, uv.last.toDouble.mps))
      }
    }.persist()


    // exposed values for closures
    val max_i = field.map { case (cell, vel) => cell.i }.max()
    val max_j = field.map { case (cell, vel) => cell.j }.max()

    val dx = config.width / max_i
    val dy = config.height / max_j

    val x = config.x
    val y = config.y
    val width = config.width
    val height = config.height

    // function for converting from meters -> grid
    val toGridPoint = (xy: Position2) =>
      GridPoint2(((xy.x - x) * max_i / width).floor.toLong,
        ((xy.y - y) * max_j / height).floor.toLong)

    // a function for converting from grid -> meters
    val toWorldPosition = (ij: GridPoint2) => Position2(ij.i * dx + x, ij.j * dy + y)

    // given a lower left grid point (not cell), this gives the 4 surrounding points for a cell
    val enclosing = field.keys
      .filter { cell => (cell.i < max_i) && (cell.j < max_j) }
      .flatMap { cell => Array((cell, (cell, 0)),
        (cell, (GridPoint2(cell.i + 1, cell.j), 2)),
        (cell, (GridPoint2(cell.i, cell.j + 1), 1)),
        (cell, (GridPoint2(cell.i + 1, cell.j + 1), 3)))
    }.persist()

    // wind class that has all of the data
    new Wind2D(field.persist(),
      Distance2(config.width, config.height),
      Position2(config.x, config.y),
      GridPoint2(max_i, max_j),
      toGridPoint, toWorldPosition,
      enclosing.persist(),
      Distance2(dx, dy))
    // TODO: reverse UV based on Event Reconstruction flag.
  }
}
