name := """Scala-Shop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).aggregate(myLibrary).dependsOn(myLibrary)

lazy val myLibrary = project

scalaVersion := "2.11.2"

scalacOptions in (Compile, doc) += s"-doc-external-doc:${scalaInstance.value.libraryJar}#http://www.scala-lang.org/api/${scalaVersion.value}/"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  jdbc,
  anorm,
  cache,
  ws
)
