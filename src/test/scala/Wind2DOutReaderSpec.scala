/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.motion.VelocityConversions._
import squants.space.Meters
import scala.language.postfixOps

class Wind2DOutReaderSpec extends UnitSpecWithLocalSparkContext {
  val wind2d_out = "src/main/resources/20141020-0600_wind2d.out"
  var windField: Wind2D = _

  val wind2d_inp = "src/main/resources/wind2d.inp"
  var wind2d_config: Wind2DConfig = _

  describe("Wind2D Output Reader") {
    it("takes a filename and return a 2D wind field") {
      wind2d_config = Wind2DConfigReader(wind2d_inp)
      windField = Wind2DOutReader(wind2d_out, wind2d_config)
    }
  }

  describe("Test Wind 2D field read") {
    it("has 201*201 elements") {
      windField.velocity.count should equal(201 * 201)
    }
    it("has only one unique UV pair") {
      windField.velocity.values.distinct().count should equal(1)
    }
    it("is (1.0 m/s, 1.0 m/s)") {
      windField.velocity.values.distinct().first should equal(Velocity2(1.0 mps, 1.0 mps))
    }
    it("neighbors of (0, 0) are correct") {
      val origin = sc.parallelize(Array((GridPoint2(0L, 0L), 1)))
      val contains = windField.enclosing.join(origin).values.map { case i => (i._1)._1 }.collect()
      contains.length should equal(4)
      contains.contains(GridPoint2(0L, 0L)) should equal(true)
      contains.contains(GridPoint2(1L, 0L)) should equal(true)
      contains.contains(GridPoint2(0L, 1L)) should equal(true)
      contains.contains(GridPoint2(1L, 1L)) should equal(true)
    }
    it("neighbors of (1, 1) are correct") {
      val origin = sc.parallelize(Array((GridPoint2(1L, 1L), 1)))
      val contains = windField.enclosing.join(origin).values.map { case i => (i._1)._1 }.collect()
      contains.length should equal(4)
      contains.contains(GridPoint2(1L, 1L)) should equal(true)
      contains.contains(GridPoint2(2L, 1L)) should equal(true)
      contains.contains(GridPoint2(1L, 2L)) should equal(true)
      contains.contains(GridPoint2(2L, 2L)) should equal(true)
    }
    it("no neighbors of (200, 200)") {
      val origin = sc.parallelize(Array((GridPoint2(200L, 200L), 1)))
      val contains = windField.enclosing.join(origin).values.map { case i => (i._1)._1 }.collect()
      contains.length should equal(0)
    }
    it("neighbors of (199, 199) are correct") {
      val origin = sc.parallelize(Array((GridPoint2(199L, 199L), 1)))
      val contains = windField.enclosing.join(origin).values.map { case i => (i._1)._1 }.collect()
      contains.length should equal(4)
      contains.contains(GridPoint2(199L, 199L)) should equal(true)
      contains.contains(GridPoint2(200L, 199L)) should equal(true)
      contains.contains(GridPoint2(199L, 200L)) should equal(true)
      contains.contains(GridPoint2(200L, 200L)) should equal(true)
    }
    it("we get (0, 0) for (50000.0 m, 1950800.0 m)") {
      windField.toGridPoint(Position2(Meters(50000.0), Meters(1950800.0))) should equal(GridPoint2(0L, 0L))
      windField.toWorldPosition(GridPoint2(0L, 0L)) should equal(Position2(Meters(50000.0), Meters(1950800.0)))
    }
    it("we get (200, 200) for (250000.0 m, 2150800.0 m)") {
      windField.toGridPoint(Position2(Meters(250000.0), Meters(2150800.0))) should equal(GridPoint2(200L, 200L))
      windField.toWorldPosition(GridPoint2(200L, 200L)) should equal(Position2(Meters(250000.0), Meters(2150800.0)))
    }
  }

}
