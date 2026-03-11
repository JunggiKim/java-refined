# Repository Checklist

These steps are intentionally manual because this workspace is local-only.

## GitHub Repository Setup

- create public repository `JunggiKim/java-refined`
- copy or split the contents of `oss/java-refined` into the repository root
- set repository description to `Refinement and functional value types for Java 8+`
- add topics:
  - `java`
  - `java8`
  - `functional-programming`
  - `refinement-types`
  - `validation`
  - `non-empty`
  - `maven-central`
- enable Issues
- enable Releases
- leave Discussions disabled unless community traffic justifies it

## Branch Protection

- protect `main`
- require pull request before merge
- require passing checks:
  - `ci`
  - `wrapper-validation`
- disallow force push
- disallow direct push for normal development

## Secrets for Release

- `JRELEASER_GITHUB_TOKEN`
- `JRELEASER_MAVENCENTRAL_USERNAME`
- `JRELEASER_MAVENCENTRAL_PASSWORD`
- `JRELEASER_GPG_SECRET_KEY`
- `JRELEASER_GPG_PASSPHRASE`

## First Release Checklist

- change version from snapshot to release
- update `CHANGELOG.md`
- confirm the standalone public repository is wired to the `ci` and `wrapper-validation` workflows
- confirm `ci` and `wrapper-validation` are green on the release commit
- run `./gradlew clean check testJava8`
- run `./gradlew prepareRelease`
- optionally run `./gradlew verifyJreleaserConfig` with release secrets
- push commit and tag
- verify GitHub Release
- verify Maven Central resolution
- bump to next snapshot
