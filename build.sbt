ThisBuild / organization := "org.scala-exercises"
ThisBuild / githubOrganization := "47degrees"
ThisBuild / scalaVersion := V.scala
ThisBuild / crossScalaVersions := Seq(V.scala212, V.scala)

publish / skip := true

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val V = new {
  val cats: String      = "2.1.1"
  val circe: String     = "0.13.0"
  val classutil: String = "1.5.1"
  val http4s: String    = "0.21.4"
  val scala: String     = "2.13.2"
  val scala212: String  = "2.12.11"
  val scalatest: String = "3.1.2"
}

lazy val runtime = project
  .dependsOn(`evaluator-client`)
  .settings(name := "runtime")
  .settings(
    libraryDependencies ++= Seq(
      "org.clapper"   %% "classutil" % V.classutil,
      "org.typelevel" %% "cats-core" % V.cats % Compile,
      "org.scalatest" %% "scalatest" % V.scalatest % Test
    )
  )

lazy val `evaluator-client` = project
  .settings(
    name := "evaluator-client",
    libraryDependencies ++= Seq(
      "org.http4s"    %% "http4s-blaze-client" % V.http4s,
      "org.http4s"    %% "http4s-circe"        % V.http4s,
      "io.circe"      %% "circe-core"          % V.circe,
      "io.circe"      %% "circe-generic"       % V.circe,
      "org.scalatest" %% "scalatest"           % V.scalatest % Test
    )
  )

lazy val `project-docs` = (project in file(".docs"))
  .aggregate(runtime, `evaluator-client`)
  .settings(moduleName := "runtime-project-docs")
  .settings(mdocIn := file(".docs"))
  .settings(mdocOut := file("."))
  .settings(skip in publish := true)
  .enablePlugins(MdocPlugin)
