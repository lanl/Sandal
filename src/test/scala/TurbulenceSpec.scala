/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.motion.MetersPerSecond

class TurbulenceSpec extends UnitSpecWithLocalSparkContext {
  val turbulence = HomogeneousTurbulence(MetersPerSecond(1.0), MetersPerSecond(1.0), MetersPerSecond(1.0), 100000)

  describe("Homogeneous Turbulence") {
    it("should generate and export velocity perturbation to each particles") {
      turbulence.velocity.count() should equal(100000)
    }
    it("should generate perturbation to x component of the wind field with mean close to zero.") {
      turbulence.velocity.map { case vel => vel.u to MetersPerSecond }.mean() should equal(0.0 +- 0.01)
    }
    it("should generate perturbation to x component of the wind field with stddev close to specified value.") {
      turbulence.velocity.map { case vel => vel.u to MetersPerSecond }.stdev() should equal(1.0 +- 0.01)
    }
    it("should generate perturbation to y component of the wind field with mean close to zero.") {
      turbulence.velocity.map { case vel => vel.v to MetersPerSecond }.mean() should equal(0.0 +- 0.01)
    }
    it("should generate perturbation to y component of the wind field with stddev close to specified value.") {
      turbulence.velocity.map { case vel => vel.v to MetersPerSecond }.stdev() should equal(1.0 +- 0.01)
    }
    it("should generate perturbation to z component of the wind field with mean close to zero.") {
      turbulence.velocity.map { case vel => vel.w to MetersPerSecond }.mean() should equal(0.0 +- 0.01)
    }
    it("should generate perturbation to z component of the wind field with stddev close to specified value.") {
      turbulence.velocity.map { case vel => vel.w to MetersPerSecond }.stdev() should equal(1.0 +- 0.01)
    }
  }
}
