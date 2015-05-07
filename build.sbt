name := """Scala-Shop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(shopCrawler,shopModel)

lazy val shopCrawler = (project in file("Shop-Crawler")).enablePlugins(PlayScala).dependsOn(shopModel)

lazy val shopModel = (project in file("Shop-Model")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

scalacOptions in (Compile, doc) += s"-doc-external-doc:${scalaInstance.value.libraryJar}#http://www.scala-lang.org/api/${scalaVersion.value}/"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  jdbc,
  anorm,
  cache,
  ws
)
