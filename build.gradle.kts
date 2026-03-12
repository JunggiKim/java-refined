import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    `java-library`
    `maven-publish`
    jacoco
    checkstyle
    pmd
    id("com.github.spotbugs") version "6.4.8"
    id("info.solidsoft.pitest") version "1.15.0"
}

val groupId = property("group") as String
val libraryVersion = property("version") as String
val junitVersion = property("junitVersion") as String
val jetbrainsAnnotationsVersion = property("jetbrainsAnnotationsVersion") as String
val projectUrl = "https://github.com/JunggiKim/java-refined"
val developerId = "junggikim"
val developerName = "Junggi Kim"

group = groupId
version = libraryVersion

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    compileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    testCompileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val testSourceSet = the<SourceSetContainer>().named("test")
val java8TestLauncher = javaToolchains.launcherFor {
    languageVersion.set(JavaLanguageVersion.of(8))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.register<Test>("testJava8") {
    description = "Runs the test suite on a Java 8 runtime via Gradle toolchains."
    group = "verification"
    testClassesDirs = testSourceSet.get().output.classesDirs
    classpath = testSourceSet.get().runtimeClasspath
    javaLauncher.set(java8TestLauncher)
    shouldRunAfter(tasks.named<Test>("test"))
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:-options")
    if (JavaVersion.current().isJava9Compatible) {
        options.release.set(8)
    }
}

tasks.withType<Javadoc>().configureEach {
    val javadocOptions = options as StandardJavadocDocletOptions
    javadocOptions.encoding = "UTF-8"
    javadocOptions.charSet = "UTF-8"
    javadocOptions.addStringOption("Xdoclint:all,-missing", "-quiet")
    if (JavaVersion.current().isJava9Compatible) {
        javadocOptions.addBooleanOption("html5", true)
    }
    title = "Java Refined ${project.version} API"
}

jacoco {
    toolVersion = "0.8.12"
}

checkstyle {
    toolVersion = "10.21.1"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.withType<org.gradle.api.plugins.quality.Checkstyle>().configureEach {
    isIgnoreFailures = false
}

spotbugs {
    ignoreFailures.set(false)
    effort.set(Effort.MAX)
    reportLevel.set(Confidence.MEDIUM)
    showProgress.set(true)
    excludeFilter.set(file("config/spotbugs/exclude.xml"))
}

pmd {
    toolVersion = "7.10.0"
    ruleSetFiles = files("config/pmd/ruleset.xml")
    ruleSets = listOf()
}

tasks.named("pmdTest") {
    enabled = false
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            limit {
                counter = "COMPLEXITY"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            limit {
                counter = "CLASS"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
        }
    }
}

pitest {
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("io.github.junggikim.refined.*"))
    targetTests.set(listOf("io.github.junggikim.refined.*"))
    threads.set(Runtime.getRuntime().availableProcessors())
    outputFormats.set(listOf("XML", "HTML"))
    timestampedReports.set(false)
    mutators.set(listOf("DEFAULTS"))
    mutationThreshold.set(95)
    excludedMethods.set(listOf("secureXmlFactory"))
}

tasks.named("check") {
    dependsOn(
        tasks.jacocoTestCoverageVerification,
        "spotbugsMain",
        "spotbugsTest"
    )
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "java-refined"

            pom {
                name.set("Java Refined")
                description.set("Refinement and functional value types for Java 8+.")
                url.set(projectUrl)
                inceptionYear.set("2026")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                        url.set("https://github.com/JunggiKim")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/JunggiKim/java-refined.git")
                    developerConnection.set("scm:git:ssh://git@github.com/JunggiKim/java-refined.git")
                    url.set(projectUrl)
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("$projectUrl/issues")
                }
            }
        }
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Automatic-Module-Name"] = "io.github.junggikim.refined"
    }
}
