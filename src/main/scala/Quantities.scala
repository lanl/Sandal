/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.motion.Velocity
import squants.space.Length
import squants.time.Time

// TODO: Implicit conversion
final case class Position2(x: Length, y: Length) {
  def +(distance: Distance2) = Position2(x + distance.x, y + distance.y)

  def -(position: Position2) = Distance2(x - position.x, y - position.y)
}

final case class Position3(x: Length, y: Length, z: Length) {
  def +(distance: Distance3) =
    Position3(x + distance.x, y + distance.y, z + distance.z)

  def -(position: Position3) =
    Distance3(x - position.x, y - position.y, z - position.z)
}

final case class Vector2(x: Length, y: Length)

final case class Vector3(x: Length, y: Length, z: Length)

final case class Distance2(x: Length, y: Length)

final case class Distance3(x: Length, y: Length, z: Length)

final case class Velocity2(u: Velocity, v: Velocity) {
  def *(time: Time) = Distance2(u * time, v * time)

  def +(vel: Velocity2) = Velocity2(u + vel.u, v + vel.v)
}

final case class Velocity3(u: Velocity, v: Velocity, w: Velocity) {
  def *(time: Time) = Distance3(u * time, v * time, w * time)

  def +(vel: Velocity3) = Velocity3(u + vel.u, v + vel.v, w + vel.w)
}

final case class GridPoint2(i: Long, j: Long)

final case class GridPoint3(i: Long, j: Long, k: Long)
