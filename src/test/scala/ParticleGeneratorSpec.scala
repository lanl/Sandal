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

class ParticleGeneratorSpec extends UnitSpecWithLocalSparkContext {
  describe("ParticleGenerator with a PointSource") {
    val particles = ParticleGenerator(PointSource(Meters(4.0), Meters(5.0), Meters(6.0)), 10)

    it("should generate number of Particles as specified") {
      particles.position.count() should equal(10)
    }
    it("should generate particles with all the same positions as the PointSource") {
      particles.position.distinct().count() should equal(1)
      particles.position.distinct().first() should equal(Position3(Meters(4.0), Meters(5.0), Meters(6.0)))
    }
  }
}
