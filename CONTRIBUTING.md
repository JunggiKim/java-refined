# Contributing

Thanks for considering a contribution to Java Refined.

## Development Principles

- keep runtime dependencies at zero
- preserve Java 8+ compatibility
- prefer explicit, immutable value types
- use TDD for behavior changes
- do not weaken the JaCoCo `100%` coverage gate

## Local Development

Run commands from the repository root.
Run Gradle on JDK 17+.
Keep a locally discoverable JDK 8 installed for the `testJava8` toolchain task.

Use this baseline verification command for normal changes:

```bash
./gradlew clean check testJava8 jacocoTestReport
```

No additional release pipeline is required.

## Branch and Commit Expectations

- use small, reviewable commits
- keep unrelated refactors out of feature or bug-fix changes
- update documentation when public behavior changes
- keep `CHANGELOG.md` aligned with release-facing changes

## Pull Requests

Each pull request should include:

- a clear problem statement
- the behavior change or design change
- test coverage for new logic
- any compatibility or migration note if relevant

## What Not to Change Casually

- group/artifact/package coordinates
- Java baseline
- `Automatic-Module-Name`
- coverage verification rules

Changes to those areas should be explained explicitly in the pull request description.
