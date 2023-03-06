ThisBuild / organization       := "org.scala-exercises"
ThisBuild / githubOrganization := "47degrees"
ThisBuild / scalaVersion       := "2.13.10"
ThisBuild / crossScalaVersions := Seq("2.13.10", "2.12.17")

publish / skip := true

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; +test")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val V = new {
  val cats: String      = "2.9.0"
  val circe: String     = "0.14.5"
  val classutil: String = "1.5.1"
  val http4s: String    = "0.23.13"
  val scalatest: String = "3.2.15"
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
