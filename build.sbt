name := """Scala-Shop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions in (Compile, doc) += s"-doc-external-doc:${scalaInstance.value.libraryJar}#http://www.scala-lang.org/api/${scalaVersion.value}/"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.21",
  jdbc,
  anorm,
  cache,
  ws
)
