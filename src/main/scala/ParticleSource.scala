/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.Length

abstract class ParticleSource {
  def generate: Position3
}

final class PointSource(position: Position3) extends ParticleSource {
  val generate = position
}

object PointSource {
  def apply(x: Length, y: Length, z: Length) = new PointSource(Position3(x, y, z))
  def apply(position: Position3) = new PointSource(position)
}
