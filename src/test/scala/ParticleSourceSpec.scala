/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.Meters
import squants.space.LengthConversions._
import scala.language.postfixOps

class ParticleSourceSpec extends UnitSpec {
  describe("PointSource") {
    it("can be created from a Position3") {
      val position = Position3(1.0 meters, 2.0 meters, 3.0 meters)
      val source = PointSource(position)
      source.generate should equal(position)
    }
    it("can be created from x, y, z components of its location") {
      val source = PointSource(1.0 meters, 2.0 meters, 3.0 meters)
    }
    it("constantly generates positions with the same (x, y ,z) as its parameter") {
      val source = PointSource(Meters(1.0), Meters(2.0), Meters(3.0))
      val p0 = source.generate
      val p1 = source.generate
      p0 should equal(Position3(Meters(1.0), Meters(2.0), Meters(3.0)))
      p1 should equal(Position3(Meters(1.0), Meters(2.0), Meters(3.0)))
    }
  }
}