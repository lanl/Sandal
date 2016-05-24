/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.{Kilometers, Length, Meters}

import scala.io.Source

class Wind2DConfig {
  // Width and height of simulation domain in meter
  var width: Length = _ // Lx_Wind2D
  var height: Length = _ // Ly_Wind2D

  // UTM coordinate of southwest corner of the domain, in meter
  var x: Length = _ // x_southwest
  var y: Length = _ // y_southwest
}

object Wind2DConfigReader {
  def apply(filename: String): Wind2DConfig = {
    val config = new Wind2DConfig
    val lines = Source.fromFile(filename).getLines()

    // Values read in are in kilometer, convert to meter
    config.width = Kilometers(lines.next().split("""\s""").head.toDouble) in Meters
    config.height = Kilometers(lines.next().split("""\s""").head.toDouble) in Meters

    // skip lines on alpha, SOR relaxation factor and interpolation forcing factor
    for (i <- 1 to 3) {
      lines.next()
    }

    // Values read in are in kilometer, convert to meter
    // ToDo: validate values read in as in the original Fortran code.
    config.x = Kilometers(lines.next().split("""\s""").head.toDouble) in Meters
    config.y = Kilometers(lines.next().split("""\s""").head.toDouble) in Meters

    config
  }
}
