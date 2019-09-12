lazy val akkaHttpVersion = "10.1.9"
lazy val akkaVersion = "2.6.0-M5"
lazy val specs2 = "4.6.0"
lazy val jodaTime = "2.9.4"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "gov.uk.homeoffice.drt",
      scalaVersion := "2.12.8"
    )),
    name := "drt-cirium",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "joda-time" % "joda-time" % jodaTime,

      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.specs2" %% "specs2-core" % specs2 % Test
    )
  )
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)

fork in run := true

publishTo := {
  val artifactory = "https://artifactory.digital.homeoffice.gov.uk/"

  if (isSnapshot.value)
    Some("snapshot" at artifactory + "artifactory/libs-snapshot-local")
  else
    Some("release" at artifactory + "artifactory/libs-release-local")
}

//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

// Enable publishing the jar produced by `test:package`
publishArtifact in(Test, packageBin) := true

// Enable publishing the test API jar
publishArtifact in(Test, packageDoc) := true

// Enable publishing the test sources jar
publishArtifact in(Test, packageSrc) := true