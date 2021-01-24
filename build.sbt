name := "akka-cron-demo"
organization := "com.amdelamar"
version := "1.0"
scalaVersion := "2.13.4"

val AkkaVersion = "2.6.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.2.2",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.5-akka-2.6.x",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

lazy val root = (project in file("."))
