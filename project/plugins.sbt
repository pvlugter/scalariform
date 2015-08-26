resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.github.retronym" % "sbt-onejar" % "0.8")

resolvers += Classpaths.typesafeSnapshots

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.2.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.0.1")

resolvers += Resolver.url("sbt-plugin-releases", new URL("https://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.2.2")

retrieveManaged := true

