/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import java.io.File

import squants.space.LengthConversions._

import scala.io.Source
import scala.language.postfixOps

class ParticleWriterSpec extends UnitSpecWithLocalSparkContext {
  val particles = ParticleGenerator(PointSource(4.0 meters, 5.0 meters, 6.0 meters), 10)
  val path = "particles.txt"

  override def afterAll() {
    // delete temp file created by tests
    new File(path).deleteOnExit()
    // call super class method to shutdown SparkContext
    super.afterAll()
  }

  describe("A ParticleWriter") {
    it("should be constructed from an output file name and Particles") {
      ParticleWriter(path, particles)
    }
    it("should create a file that persists afterward") {
      
      ParticleWriter(path, particles)
      val file = new File(path)
      file.isFile should equal(true)
    }
    it("should write the same number of lines as the number of particles") {
      Source.fromFile(path).getLines().length should equal(particles.position.count())
    }
  }

}
