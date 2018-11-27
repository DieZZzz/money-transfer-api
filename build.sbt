name := "money-transfer-api"

version := "1.0"

PlayKeys.externalizeResources := false

lazy val `money` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

val monetaVersion = "1.3"

val h2DatabaseVersion = "1.4.197"
val hibernateVersion = "5.2.17.Final"

val assertJVersion = "3.11.1"
val mockitoVersion = "2.23.4"

libraryDependencies ++= Seq(
  guice,
  javaJpa,
  "org.javamoney" % "moneta" % monetaVersion,

  "com.h2database" % "h2" % h2DatabaseVersion,
  "org.hibernate" % "hibernate-core" % hibernateVersion,

  "org.assertj" % "assertj-core" % assertJVersion % "test",
  "org.mockito" % "mockito-core" % mockitoVersion % "test"
)

      