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
import org.apache.spark.mllib.random.RandomRDDs
import org.apache.spark.rdd.RDD
import squants.motion.Velocity

// Perturbation to the wind velocity field at the position of particles.
abstract class Turbulence {
  val velocity: RDD[Velocity3]
}

// Turbulence with constant variances in x, y, z directions (i.e. sigma_u,
// sigma_v, sigma_w) for all particle positions and all time steps, this
// corresponds to flag_turb = 1 in the original Fortran code. Notice that the
// perturbations 'u', 'v' 'w' are NOT constant over time, i.e. they are updated
// at every single time step according to some complicated formula.
final class HomogeneousTurbulence(val velocity: RDD[Velocity3]) extends Turbulence

object HomogeneousTurbulence {
  def apply(sigma_u: Velocity, sigma_v: Velocity, sigma_w: Velocity,
            nParticles: Long)(implicit sc: SparkContext) = {

    val u = RandomRDDs.normalRDD(sc, nParticles, sc.defaultParallelism).map(sigma_u * _)
    val v = RandomRDDs.normalRDD(sc, nParticles, sc.defaultParallelism).map(sigma_v * _)
    val w = RandomRDDs.normalRDD(sc, nParticles, sc.defaultParallelism).map(sigma_w * _)

    val uvw = (u zip v) zip w map {
      case ((x, y), z) => Velocity3(x, y, z)
    }

    new HomogeneousTurbulence(uvw.persist())
  }
}
