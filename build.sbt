name := """play-ether-elastic"""
organization := "com.knoldus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.web3j" % "core" % "3.5.0"
libraryDependencies += "org.elasticsearch.client" % "transport" % "6.4.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "org.scala-lang.modules" % "scala-java8-compat_2.12" % "0.9.0"
libraryDependencies += specs2 % Test