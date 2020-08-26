name := "log-collector"

version := "0.1"

scalaVersion := "2.13.3"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "org.apache.logging.log4j" % "log4j-api" % "2.13.3",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.3",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.13.3",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.10.2",
  "org.apache.kafka" % "kafka-clients" % "2.6.0"
)
