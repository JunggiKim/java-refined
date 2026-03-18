import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    id("com.vanniktech.maven.publish") version "0.35.0"
    jacoco
}

val groupId = rootProject.property("group") as String
val libraryVersion = rootProject.property("version") as String
val junitVersion = rootProject.property("junitVersion") as String
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
}

dependencies {
    api(project(":"))
    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.6.0")
    testImplementation(kotlin("test"))
    testImplementation(gradleTestKit())
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val testSourceSet = the<SourceSetContainer>().named("test")
val java8TestLauncher = javaToolchains.launcherFor {
    languageVersion.set(JavaLanguageVersion.of(8))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xjsr305=strict"
    }
}

tasks.register<Test>("testJava8") {
    description = "Runs the Kotlin support test suite on a Java 8 runtime via Gradle toolchains."
    group = "verification"
    testClassesDirs = testSourceSet.get().output.classesDirs
    classpath = testSourceSet.get().runtimeClasspath
    javaLauncher.set(java8TestLauncher)
    shouldRunAfter(tasks.named<Test>("test"))
}

jacoco {
    toolVersion = "0.8.12"
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

tasks.named("check") {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()
    coordinates(groupId, "java-refined-kotlin", libraryVersion)
    pom {
        name.set("Java Refined Kotlin Support")
        description.set("Kotlin/JVM adapters and extensions for Java Refined.")
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
            connection.set("scm:git:git://github.com/JunggiKim/java-refined.git")
            developerConnection.set("scm:git:ssh://github.com/JunggiKim/java-refined.git")
            url.set(projectUrl)
        }
        issueManagement {
            system.set("GitHub Issues")
            url.set("$projectUrl/issues")
        }
    }
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Automatic-Module-Name"] = "io.github.junggikim.refined.kotlin"
    }
}
