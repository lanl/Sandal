/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.motion.VelocityConversions._
import squants.space.LengthConversions._
import squants.time.TimeConversions._
import scala.language.postfixOps

class QuantitiesSpec extends UnitSpec {
  describe("Position2") {
    it("should be constructed by x and y components") {
      val pos = Position2(1 meters, 2 meters)
      pos.x should equal(1 meters)
      pos.y should equal(2 meters)
    }
    it("can be added to by a Distance2 and become another Poistion2") {
      val pos = Position2(1 meters, 2 meters)
      val dis = Distance2(3 meters, 4 meters)
      pos + dis should equal(Position2(4.meters, 6.meters))
    }
    it("can be subtracted by another Position2 to get the Distance2 between them") {
      val pos1 = Position2(1 meters, 2 meters)
      val pos2 = Position2(4 meters, 6 meters)
      pos2 - pos1 should equal(Distance2(3 meters,4 meters ))
    }
  }
  describe("Distance2") {
    it("should be constructed by x and y components") {
      val dis = Distance2(2 meters, 3 meters)
      dis.x should equal(2 meters)
      dis.y should equal(3 meters)
    }
  }
  describe("Velocity2") {
    it("should be constructed by u and v components") {
      val vel = Velocity2(1 mps, 2 mps)
      vel.u should equal(1 mps)
      vel.v should equal(2 mps)
    }
    it("can be multiplied by time to get Distance2") {
      val vel = Velocity2(1 mps, 2 mps)
      val dt = 5.seconds
      vel * dt should equal(Distance2(5 meters, 10 meters))
    }
    it("can be added to by a Velocity2 to become another Velocity2") {
      val vel1 = Velocity2(1 mps, 2 mps)
      val vel2 = Velocity2(3 mps, 4 mps)
      vel1 + vel2 should equal(Velocity2(4 mps, 6 mps))
    }
  }
  describe("Position3") {
    it("should be constructed by x, y and z components") {
      val pos = Position3(1 meters, 2 meters, 3 meters)
      pos.x should equal(1 meters)
      pos.y should equal(2 meters)
      pos.z should equal(3 meters)
    }
    it("can be added to by a Distance3 to become another Position3") {
      val pos = Position3(1 meters, 2 meters, 3 meters)
      val dis = Distance3(4 meters, 5 meters, 6 meters)
      pos + dis should equal(Position3(5 meters, 7 meters, 9 meters))
    }
    it("can be subtracted by another Position3 to get the Distance3 between them") {
      val pos1 = Position3(1 meters, 2 meters, 3 meters)
      val pos2 = Position3(5 meters, 7 meters, 9 meters)
      pos2 - pos1 should equal(Distance3(4 meters, 5 meters, 6 meters))
    }
  }
  describe("Distance3") {
    it("should be constructed by x, y and z components") {
      val dis = Distance3(1 meters, 2 meters, 3 meters)
      dis.x should equal(1 meters)
      dis.y should equal(2 meters)
      dis.z should equal(3 meters)
    }
  }
  describe("Velocity3") {
    it("should be constructed by u, v and w components") {
      val vel = Velocity3(1 mps, 2 mps, 3 mps)
      vel.u should equal(1 mps)
      vel.v should equal(2 mps)
      vel.w should equal(3 mps)
    }
    it("can be multiplied by time to get Distance3") {
      val vel = Velocity3(1 mps, 2 mps, 3 mps)
      val dt = 5.seconds
      vel * dt should equal(Distance3(5 meters, 10 meters, 15 meters))
    }
    it("can be added by a Velocity3 to become another Velocity3") {
      val vel1 = Velocity3(1 mps, 2 mps, 3 mps)
      val vel2 = Velocity3(4 mps, 5 mps, 6 mps)
      vel1 + vel2 should equal(Velocity3(5 mps, 7 mps, 9 mps))
    }
  }
  describe("GridPoint2") {
    it("should be constructed from i and j indices") {
      val gridPoint = GridPoint2(1, 2)
      gridPoint.i should equal(1)
      gridPoint.j should equal(2)
    }
  }
  describe("GridPoint3") {
    it("should be constructed from i, j and k indices") {
      val gridPoint = GridPoint3(1, 2, 3)
      gridPoint.i should equal(1)
      gridPoint.j should equal(2)
      gridPoint.k should equal(3)
    }
  }
}
