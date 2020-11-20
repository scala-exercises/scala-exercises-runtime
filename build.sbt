ThisBuild / organization := "org.scala-exercises"
ThisBuild / githubOrganization := "47degrees"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / crossScalaVersions := Seq("2.13.3", "2.12.11")

publish / skip := true

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; +test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val V = new {
  val cats: String      = "2.2.0"
  val circe: String     = "0.13.0"
  val classutil: String = "1.5.1"
  val http4s: String    = "0.21.9"
  val scalatest: String = "3.2.3"
}

lazy val runtime = project
  .dependsOn(`evaluator-client`)
  .settings(name := "runtime")
  .settings(
    libraryDependencies ++= Seq(
      "org.clapper"   %% "classutil" % V.classutil,
      "org.typelevel" %% "cats-core" % V.cats      % Compile,
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

lazy val documentation = project
  .settings(mdocOut := file("."))
  .settings(publish / skip := true)
  .enablePlugins(MdocPlugin)
