/* basic project info */
name := "ficus"

description := "A Scala-friendly wrapper companion for Typesafe config"

startYear := Some(2013)

/* scala versions and options */
scalaVersion := "2.10.2"

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify",
    "-Ywarn-all",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:higherKinds"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies <++= scalaVersion { sv =>
  Seq(
    "org.specs2"     %% "specs2"         % "2.3.1"    % "test",
    "org.scalacheck" %% "scalacheck"     % "1.10.1"   % "test",
    "com.chuusai"    %  "shapeless"      % "2.0.0-M1" % "test" cross CrossVersion.full,
    "com.typesafe"   %  "config"         % "1.0.0",
    "org.scala-lang" %  "scala-reflect"  % sv % "provided")
}

/* you may need these repos */
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots")
)

/* testing */
parallelExecution in Test := true

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

offline := false

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
  ms filter { case (file, toPath) =>
      toPath != "application.conf"
  }
}

Publish.settings


