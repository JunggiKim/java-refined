# Release Process

This document describes the intended release flow for the standalone public repository.

## Versioning

- development versions use `-SNAPSHOT`
- release tags use `vX.Y.Z`
- release commits use `X.Y.Z`

## Local Preflight

Run:

```bash
./gradlew prepareRelease
```

This verifies:

- `check` quality gates (`checkstyle`, `spotbugs`, `pmd`, tests)
- Java 8 runtime compatibility through `testJava8`
- JaCoCo coverage gate
- Javadoc
- sources and Javadoc jars
- POM generation
- local Maven publication
- staging repository generation

Run `prepareRelease` on JDK 17+ and keep a local JDK 8 installed for the toolchain-backed compatibility check.

## Optional JReleaser Validation

When release credentials are available, run:

```bash
./gradlew verifyJreleaserConfig
```

This checks that the JReleaser release metadata can be materialized successfully.

This step is optional for the current workspace because the workspace is local-only
and does not perform remote publication.

## Required Environment Variables for Real Release

- `JRELEASER_GITHUB_TOKEN`
- `JRELEASER_MAVENCENTRAL_USERNAME`
- `JRELEASER_MAVENCENTRAL_PASSWORD`
- `JRELEASER_GPG_SECRET_KEY`
- `JRELEASER_GPG_PASSPHRASE`

## Intended Remote Release Flow

The hosted `release` workflow is tag-triggered only and should run only for pushed `vX.Y.Z` tags.

1. update `CHANGELOG.md`
2. set a non-snapshot version
3. create tag `vX.Y.Z`
4. push commit and tag
5. let GitHub Actions run the release workflow
6. verify GitHub Release and Maven Central publication
7. bump to next snapshot

## Local-Only Rule for This Workspace

In the current workspace, no push and no remote publication should be performed.
Only local preparation and dry-run style verification are allowed.
