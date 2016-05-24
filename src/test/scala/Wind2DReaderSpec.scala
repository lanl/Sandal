/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.Kilometers

class Wind2DReaderSpec extends UnitSpec {
  val wind2d_inp = "src/main/resources/wind2d.inp"
  var wind2d_config: Wind2DConfig = _

  describe("Wind2DReader") {
    it("takes a wind2d filename and returns a wind2d config") {
      wind2d_config = Wind2DConfigReader(wind2d_inp)
    }
    it("should read the X dimension of the wind field in km.") {
      wind2d_config.width should equal(Kilometers(200))
    }
    it("should read the Y dimension of the wind field in km.") {
      wind2d_config.height should equal(Kilometers(200))
    }
    it("should read the X coordinate of the southwest corner in km.") {
      wind2d_config.x should equal(Kilometers(50))
    }
    it("should read the Y coordinate of the southwest corner in km.") {
      wind2d_config.y should equal(Kilometers(1950.800000))
    }
  }
}
