# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Interest-based community platform with group chat. Built with Spring Boot 4.0 and JPA, following Domain-Driven Design (DDD) and Clean Architecture principles.

## Build & Test Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.findy.user.domain.UserTest"

# Run the application
./gradlew bootRun
```

## Architecture

### Domain-Driven Design Structure

The codebase follows DDD with packages organized by bounded context:
- `com.findy.user.domain` - User aggregate with value objects
- `com.findy.common.domain` - Shared domain primitives (e.g., `IntegerCounter`)

### Domain Model Patterns

- **Aggregates**: Domain entities encapsulate their own business logic (e.g., `User.follow()`, `User.unfollow()`)
- **Value Objects**: Immutable objects representing domain concepts (`UserInfo`, `SocialAccount`)
- **First-Class Collections**: Wrapper classes for collections and counters (`FollowManager`, `IntegerCounter`)

### Key Design Decisions

- `FollowManager` manages follower/following counts within the `User` aggregate. The design intentionally exposes `followManager` across users for follow operations to enable future extensibility (notifications, etc.)
- Domain objects use package-private field access rather than private with getters where appropriate