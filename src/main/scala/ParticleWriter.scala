/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import java.io.PrintStream

import squants.space.Meters

object ParticleWriter {
  def apply(path: String, particles: Particles): Unit = {
    val printStream = new PrintStream(path)

    particles.position.collect().foreach { position =>
      val line = (position.x to Meters).toString + "\t" +
        (position.y to Meters).toString + "\t" +
        (position.z to Meters).toString + "\n"
      printStream.print(line)
    }
  }
}
