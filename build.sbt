val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "taylor-fall",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
    // libraryDependencies += "io.github.pityka" %% "nspl-awt" % "0.10.0"
  )
