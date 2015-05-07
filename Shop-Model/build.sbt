name := """Shop-Model"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  jdbc
)
