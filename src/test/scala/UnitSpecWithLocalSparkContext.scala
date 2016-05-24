/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.BeforeAndAfterAll

abstract class UnitSpecWithLocalSparkContext extends UnitSpec with BeforeAndAfterAll {
  val conf = new SparkConf().setAppName("test").setMaster("local")
  implicit val sc = new SparkContext(conf)

  override def afterAll() {
    sc.stop()
  }

}
