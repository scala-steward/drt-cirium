lazy val akkaHttpVersion = "10.2.6"
lazy val akkaVersion = "2.6.8"
lazy val specs2 = "4.6.0"
lazy val jodaTime = "2.9.4"
lazy val logBackClassicVersion = "1.1.3"
lazy val logbackContribVersion = "0.1.5"
lazy val jacksonDatabindVersion = "2.10.0"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "uk.gov.homeoffice",
      scalaVersion := "2.13.6",
    )),
    version := sys.env.getOrElse("DRONE_BUILD_NUMBER", sys.env.getOrElse("BUILD_ID", "DEV")),
    name := "drt-cirium",

    resolvers += "Artifactory Realm" at "https://artifactory.digital.homeoffice.gov.uk/",
    resolvers += "Artifactory Realm release local" at "https://artifactory.digital.homeoffice.gov.uk/artifactory/libs-release-local/",

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "joda-time" % "joda-time" % jodaTime,

      "ch.qos.logback.contrib" % "logback-json-classic" % logbackContribVersion,
      "ch.qos.logback.contrib" % "logback-jackson" % logbackContribVersion,
      "com.fasterxml.jackson.core" % "jackson-databind" % jacksonDatabindVersion,
      "org.codehaus.janino" % "janino" % "3.0.7",

      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.specs2" %% "specs2-core" % specs2 % Test
    )
  )
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)

run / fork := true

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
