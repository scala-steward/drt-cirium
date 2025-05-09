lazy val scala = "2.13.16"
lazy val pekkoVersion = "1.1.3"
lazy val pekkoHttpVersion = "1.1.0"
lazy val specs2Version = "4.20.9"
lazy val jodaTimeVersion = "2.12.7"
lazy val logBackClassicVersion = "1.4.14"
lazy val logbackContribVersion = "0.1.5"
lazy val jacksonDatabindVersion = "2.16.1"
lazy val censorinusVersion = "2.1.16"
lazy val scalatestVersion = "3.2.19"
lazy val janinoVersion = "3.1.11"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "uk.gov.homeoffice",
    )),
    scalaVersion := scala,
    version := "v" + sys.env.getOrElse("DRONE_BUILD_NUMBER", sys.env.getOrElse("BUILD_ID", "DEV")),
    name := "drt-cirium",

    resolvers ++= Seq(
      "Akka library repository".at("https://repo.akka.io/maven"),
      "Artifactory Realm libs release local" at "https://artifactory.digital.homeoffice.gov.uk/artifactory/libs-release-local/",
    ),

    dockerBaseImage := "openjdk:11-jre-slim-buster",

    libraryDependencies ++= Seq(
      "com.github.gphat" %% "censorinus" % censorinusVersion,
      "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
      "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
      "org.apache.pekko" %% "pekko-pki" % pekkoVersion,
      "ch.qos.logback" % "logback-classic" % logBackClassicVersion,
      "joda-time" % "joda-time" % jodaTimeVersion,

      "ch.qos.logback.contrib" % "logback-json-classic" % logbackContribVersion,
      "ch.qos.logback.contrib" % "logback-jackson" % logbackContribVersion,
      "com.fasterxml.jackson.core" % "jackson-databind" % jacksonDatabindVersion,
      "org.codehaus.janino" % "janino" % janinoVersion,

      "org.apache.pekko" %% "pekko-http-testkit" % pekkoHttpVersion % Test,
      "org.apache.pekko" %% "pekko-testkit" % pekkoVersion % Test,
      "org.apache.pekko" %% "pekko-stream-testkit" % pekkoVersion % Test,
      "org.specs2" %% "specs2-core" % specs2Version % Test,
      "org.scalactic" %% "scalactic" % scalatestVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    )
  )
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)

Test / fork := true

publishTo := {
  val artifactory = "https://artifactory.digital.homeoffice.gov.uk/"

  if (isSnapshot.value)
    Some("snapshot" at artifactory + "artifactory/libs-snapshot-local")
  else
    Some("release" at artifactory + "artifactory/libs-release-local")
}

// Enable publishing the jar produced by `test:package`
Test / packageBin / publishArtifact := true

// Enable publishing the test API jar
Test / packageDoc / publishArtifact := true

// Enable publishing the test sources jar
Test / packageSrc / publishArtifact := true
