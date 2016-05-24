name := "Sandal"

version := "0.1.0"

scalaVersion := "2.10.6"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies := Seq(
  "com.squants"  %% "squants"  % "0.6.1-SNAPSHOT",
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test")


// Forking a new JVM instance for each test such that each test has its own
// SparkContext.
fork := true