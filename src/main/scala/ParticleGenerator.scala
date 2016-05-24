
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

final class Particles(val position: RDD[Position3])

object Particles {
  def apply(position: RDD[Position3]): Particles = new Particles(position)
}

object ParticleGenerator {
  def apply(source: ParticleSource, nParticles: Long)
           (implicit sc: SparkContext): Particles =
    new Particles(sc.parallelize((0L until nParticles).map(n => source.generate)))
}
