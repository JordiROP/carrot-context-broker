ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val http4sVersion = "0.23.16"

lazy val root = (project in file("."))
  .settings(
    name := "CarrotContextBroker",
    idePackagePrefix := Some("org.onru.carrotcb")
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.14" % Test,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "eu.timepit" %% "refined" % "0.10.1"
)