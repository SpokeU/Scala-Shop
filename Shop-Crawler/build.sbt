name := """Shop-Crawler"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.2",
  "org.jsoup" % "jsoup" % "1.8.1",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.0.4",
  "net.anthavio" % "phanbedder-1.9.2" % "1.0.0",
  "org.seleniumhq.selenium" % "selenium-java" % "2.45.0"
)
