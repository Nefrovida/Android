# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.0] - 2025-10-22

### Added

- Project documentation setup
- Initial project setup
- README.md with project documentation
- CONTRIBUTING.md with development guidelines
- Pull Request template
- Conventional Commits standard
- Git Flow branching strategy

---

## [0.1.0] - 2025-10-25

### Added

- Front-end routing

### Types of changes

- **Added** for new features
- **Changed** for changes in existing functionality
- **Deprecated** for soon-to-be removed features
- **Removed** for now removed features
- **Fixed** for any bug fixes
- **Security** in case of vulnerabilities

### Guidelines

1. **Always update the Unreleased section** when making changes
2. **Use clear, user-focused language** (not technical jargon)
3. **Link to relevant PRs or issues** when applicable
4. **Group related changes** together
5. **Keep it concise** but informative

### Version format

```
## [Major.Minor.Patch] - YYYY-MM-DD
```

- **Major**: Breaking changes
- **Minor**: New features (backward compatible)
- **Patch**: Bug fixes (backward compatible)

### Example entries

```markdown
## [Unreleased]

### Added

- User authentication with OAuth2 support (#123)
- Export functionality for CSV and JSON formats (#145)

### Changed

- Improved performance of database queries by 40% (#156)
- Updated UI design for better accessibility (#134)

### Fixed

- Resolved memory leak in background worker (#167)
- Fixed validation error in login form (#142)

## [1.2.0] - 2025-10-15

### Added

- Dark mode toggle in settings (#98)
- Real-time notifications system (#102)

### Changed

- Migrated from REST to GraphQL API (#110)

### Deprecated

- Legacy v1 API endpoints (will be removed in v2.0.0)

### Security

- Patched XSS vulnerability in comment section (#115)
```

### When to create a new version

1. **Before creating a release branch**: Move Unreleased changes to a new version
2. **Use the release date**: Date when the version is released, not when changes were made
3. **Update links**: Ensure version comparison links are updated at the bottom
4. **Keep Unreleased section**: Always maintain an empty Unreleased section for ongoing work

---

[unreleased]: https://github.com/yourusername/502-example/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/yourusername/502-example/releases/tag/v0.1.0