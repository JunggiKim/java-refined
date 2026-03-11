# Compatibility

## Java Baseline

- production baseline: Java 8
- build target: Java 8 bytecode
- run Gradle on JDK 17+ because Gradle 8.13 deprecates daemon JVM 16 and lower
- CI builds run on JDK 21 to validate compatibility
- current Gradle build uses `--release 8` when running on JDK 9+
- Java 8 runtime compatibility is verified through Gradle toolchains

## Verification

The verification pipeline covers:

- `./gradlew clean check`
- `./gradlew testJava8`
- `./gradlew javadoc generatePomFileForMavenJavaPublication publishToMavenLocal`

## Notes

- `testJava8` requires a JDK 8 installation that Gradle toolchains can discover.
- The project keeps `Automatic-Module-Name` for JPMS consumers without adding `module-info.java`.
- `NonBlankString` uses Java 8-compatible blank detection.
- Parser-backed refined types rely on the behavior of JDK parsers such as `UUID`, `URI`, `URL`, `InetAddress`, XML, XPath, and number parsers.
