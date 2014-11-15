import NativePackagerKeys._

name := "hpp-prototype"

version := "0.0.1"

scalaVersion := "2.11.4"

packageArchetype.java_application

version := "0.1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo" at "http://repo.spray.io",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "com.typesafe.play" %% "play" % "2.3.6",
  "com.typesafe.play" %% "play-json" % "2.3.3",
  "io.spray" %% "spray-can" % "1.3.1",
  "io.spray" %% "spray-routing" % "1.3.1",
  "io.spray" %% "spray-http" % "1.3.1",
  "io.spray" %% "spray-httpx" % "1.3.1",
  "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
  ("org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23").
    exclude("org.apache.logging.log4j", "log4j-to-slf4j"),
  "commons-daemon" % "commons-daemon" % "1.0.15"
)
