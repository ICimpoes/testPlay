name := "testPlay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.quartz-scheduler" % "quartz" % "2.2.2",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.4.0-akka-2.3.x" withSources(),
  jdbc,
  anorm,
  cache
)

play.Project.playScalaSettings
