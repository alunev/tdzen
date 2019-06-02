name := """tdzen"""
organization := "org.alunev"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
//libraryDependencies += "org.projectlombok" % "lombok" % "1.16.16"

// https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-chrome-driver
//libraryDependencies += "org.seleniumhq.selenium" % "selenium-chrome-driver" % "2.3.1"

// https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.141.59"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5"
libraryDependencies += "org.assertj" % "assertj-core" % "3.8.0"

// https://mvnrepository.com/artifact/org.seleniumhq.selenium/htmlunit-driver
libraryDependencies += "org.seleniumhq.selenium" % "htmlunit-driver" % "2.35.1"
