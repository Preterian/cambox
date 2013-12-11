name := "formtest"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaJpa,
  cache,
  "org.hibernate" % "hibernate-entitymanager" % "4.1.7.Final",
  "org.hibernate" % "hibernate-core" % "4.1.7.Final",
  "mysql" % "mysql-connector-java" % "5.1.9",
  "org.apache.directory.api" % "api-all" % "1.0.0-M14"
)     

play.Project.playJavaSettings
