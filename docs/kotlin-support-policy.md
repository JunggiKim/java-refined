# Kotlin Support Policy

## Scope

This policy applies only to the optional `java-refined-kotlin` artifact.
The core `java-refined` artifact remains a Java 8 library with no Kotlin
runtime dependency.

## Published Baseline

- published Kotlin/JVM baseline: Kotlin `1.9.25`
- release wrapper baseline: Gradle `8.1.1`
- release build JVM: JDK `17`
- Java runtime verification target: JDK `8`

The project publishes Kotlin/JVM artifacts with Kotlin `1.9.25` to keep a
conservative compatibility floor for downstream consumers.

## Verified Compatibility Matrix

### Release Verification

| Lane | Kotlin | Purpose |
| --- | --- | --- |
| release | `1.9.25` | publishing baseline and required release lane |

### Downstream Consumer Smoke Tests

| Build Tool | Kotlin | Purpose |
| --- | --- | --- |
| Gradle Kotlin DSL | `2.2.21` | verified newer consumer line |
| Gradle Groovy DSL | `2.2.21` | verified newer consumer line |
| Maven | `2.2.21` | verified newer consumer line |

The release lane verifies the artifact as published.
The consumer smoke tests verify that the published baseline artifact can still
be consumed by a current newer Kotlin line.

## Build Tool Policy

- keep the repository wrapper on the release baseline that is fully compatible with the published Kotlin line
- do not raise the release wrapper to a newer Gradle line until the published Kotlin baseline is intentionally revised
- verify newer Kotlin lines through downstream smoke jobs instead of changing the publishing baseline immediately

## Promotion Rules

Promote a newer Kotlin line from verified consumer target to published baseline only
when all of the following are true:

- the line has been green in CI for at least two release cycles
- the downstream Gradle Kotlin DSL, Gradle Groovy DSL, and Maven smoke projects are green
- there is no remaining consumer requirement to keep the older Kotlin baseline
- the updated Gradle wrapper line is also adopted and documented

## Current Recommendation

- keep publishing with Kotlin `1.9.25`
- treat Kotlin `2.2.21` as the primary forward-compatibility target

## Non-Goals

- claiming support for every Kotlin release automatically
- moving the publishing baseline just because a newer Kotlin release exists
- coupling the published Kotlin baseline to the newest Gradle wrapper line without compatibility evidence
