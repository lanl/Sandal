/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.LengthConversions._
import scala.language.postfixOps

class SandalConfigReaderSpec extends UnitSpec {
  val random_walk_inp = "src/main/resources/random_walk.inp"
  var config: SandalConfig = _

  describe("SandalConfigReader") {
    it("takes an input filename and return a Config") {
      config = SandalConfigReader(random_walk_inp)
    }
    it("should read the debug level") {
      config.base.debug_level should equal(0)
    }
    it("should read the print on stdout flag") {
      config.base.print should equal(true)
    }
    it("should read the Event Reconstruction flag") {
      config.base.event_reconstruction should equal(false)
    }
    it("should read the run id") {
      config.sim.run_id should equal("20141020-0600")
    }
    it("should read the concentration y position") {
      config.conc.y should equal(1950000.0)
    }
    it("should read the last receptor y position") {
      config.receptor.ys(config.receptor.ys.size - 1) should equal(2.070e+06)
    }
    it("should read the power-law exponent") {
      config.wind.exponent should equal(0.25)
    }
    it("should read the calmness flag") {
      config.turb.calm should equal(true)
    }
    it("should read the u stddev") {
      config.turb_constant.stddev_u should equal(0.70)
    }
    it("should read the M-O & PGT height") {
      config.turb_mospgt.height should equal(1500.0)
    }
    it("should read the source decay rate") {
      config.source.rate should equal(1.0)
    }
    it("should read the point source z position") {
      config.source_point.position.z should equal(2.0 meters)
    }
    it("should read the number of source line segments") {
      config.source_lines.number should equal(0)
    }
    it("should read the last area rectangle z position") {
      config.source_rects.zs(config.source_rects.zs.size - 1) should equal(0.25)
    }
    it("should read the last area circle z position") {
      config.source_circles.zs(config.source_circles.zs.size - 1) should equal(0.25)
    }
  }
}
