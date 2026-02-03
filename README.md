# Findy - 관심사 기반 커뮤니티 플랫폼

관심사/취미 기반 커뮤니티 플랫폼입니다.
단순 기능 구현이 아닌 **객체지향 설계와 클린 아키텍처**에 초점을 맞춰 개발했습니다.

## 핵심 설계 원칙

| 원칙 | 적용 내용 |
|-----|----------|
| **DDD** | Bounded Context 분리, Aggregate Root 설계, 도메인 이벤트 |
| **Clean Architecture** | 계층 분리, 의존성 역전, 계층별 DTO 분리 |
| **객체지향 생활체조** | 원시값 포장, 캡슐화, 디미터 법칙 등 |

## 주요 적용 패턴

### POJO 기반 Domain Model
Domain Model은 프레임워크에 의존하지 않는 순수 Java 객체(POJO)로 설계했습니다.
- **Lombok 최소화**: `@Getter`, `@Builder` 정도만 허용하여 보일러플레이트 감소
- **Getter 사용 제한**: 객체지향 생활체조 원칙에 따라 Getter 사용을 지양하되, 단순 조회가 필요한 경우에 한해 허용
- **비즈니스 로직 내재화**: 도메인 객체가 자신의 상태를 스스로 변경 (Tell, Don't Ask)
- **Value Object 불변성**: VO는 모든 필드를 final로 선언하고, 변경 시 새 객체를 반환하는 방식으로 설계

### 객체지향 생활체조 원칙 적용

#### 원시값 포장과 책임 분리
관련 있는 원시값들을 객체로 포장하여 클래스가 비대해지는 것을 방지했습니다.

```java
// Before: 필드가 나열되어 책임이 비대해짐
public class User {
    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private String email;
    private String provider;
    private long followerCount;
    private long followingCount;
    // ...
}

// After: 관련 필드를 VO로 포장하여 책임 분리
public class User {
    private final Long id;
    private final UserInfo userInfo;           // 사용자 기본 정보
    private final SocialAccount socialAccount; // 소셜 계정 정보
    private final FollowManager followManager; // 팔로우 관리
}
```

#### 의미 있는 메서드명 사용
단순 getter/setter 대신 비즈니스 의도를 명확히 드러내는 메서드명을 사용했습니다.

```java
// Before: getter/setter로 외부에서 상태 조작 (Tell, Don't Ask 위반)
user.setFollowingCount(user.getFollowingCount() + 1);
targetUser.setFollowerCount(targetUser.getFollowerCount() + 1);

// After: 의미 있는 메서드로 행위를 표현
user.follow(targetUser);  // 내부에서 양쪽 카운트 처리 + 검증 로직 포함
```

```java
// Before: 외부에서 좋아요 로직 처리
if (!post.getAuthorId().equals(userId)) {
    post.setLikeCount(post.getLikeCount() + 1);
}

// After: 도메인 객체가 스스로 처리
post.like(userId);  // 작성자 본인 체크 등 검증 로직 내재화
```

#### 검증 로직 응집화
검증 로직이 도메인 로직과 섞여 코드가 복잡해지는 것을 방지하기 위해 내부 클래스로 분리했습니다.

```java
public class TopicInfo {
    public TopicInfo(String name, String introduction, String coverImageUrl) {
        Validator.name(name);           // 검증 로직은 Validator에 위임
        Validator.introduction(introduction);

        this.name = name;
        this.introduction = introduction;
        this.coverImageUrl = coverImageUrl;
    }

    // 검증 로직을 내부 클래스로 응집화
    private static class Validator {
        private static final int NAME_MAX_LENGTH = 50;

        private static void name(String name) {
            if (name == null || name.isBlank()) {
                throw new InvalidTopicInfoException("Name cannot be null or empty");
            }
            if (name.length() > NAME_MAX_LENGTH) {
                throw new InvalidTopicInfoException("Name cannot exceed " + NAME_MAX_LENGTH);
            }
        }
    }
}
```

### Domain Model과 JPA Entity 분리
도메인 로직은 순수 Java 객체(Domain Model)에, 영속성은 JPA Entity에 분리하여 도메인이 인프라에 오염되지 않도록 설계했습니다.

```
Domain Model (domain/model/)     JPA Entity (out/repository/entity/)
         │                                    │
         │         ┌─────────────┐            │
         └────────►│  변환 로직   │◄───────────┘
                   │ (toEntity,  │
                   │  toDomain)  │
                   └─────────────┘
```

### UseCase 패턴
비즈니스 로직을 UseCase 단위로 분리하여 단일 책임 원칙을 준수합니다.

### 계층별 DTO 분리
Controller 계층의 Request/Response DTO를 Domain과 분리했습니다.
- **도메인 보호**: 사용자 요구에 따라 API 스펙이 변경되더라도 Domain 영역이 오염되지 않음
- **데이터 구조 은닉**: 내부 객체를 그대로 반환할 경우 발생하는 데이터 구조 노출 방지
- **민감 데이터 보호**: 응답에 포함되면 안 되는 민감 데이터가 실수로 노출되는 것을 방지

변환 로직은 Mapper 클래스에서 담당하여 Controller를 깔끔하게 유지합니다.

```java
@Component
public class UserRestMapper {
    // Request → Command (Controller → UseCase)
    public CreateUserCommand toCreateCommand(CreateUserRequest req) {
        return new CreateUserCommand(req.provider(), req.email(), ...);
    }

    // Domain → Response (UseCase → Controller)
    public GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(user.getId(), user.getName(), ...);
    }
}
```

### 예외 처리 전략
Domain 예외와 Application 예외를 분리하여 예외의 성격과 책임을 명확히 구분했습니다.

| 구분 | 역할 | 예시 |
|-----|------|------|
| `DomainException` | 도메인 규칙/불변식 위반 | `SelfFollowException`, `LikeValidationException` |
| `ApplicationException` | 애플리케이션 로직 예외 (조회 실패 등) | `UserNotFoundException`, `PostNotFoundException` |

GlobalExceptionHandler에서 예외 타입별로 적절한 HTTP 상태 코드와 에러 응답을 반환합니다.

## 기술 스택

| 분류 | 기술 |
|-----|------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| ORM | Spring Data JPA, QueryDSL |
| Database | H2 (개발), MySQL (운영) |
| Documentation | SpringDoc OpenAPI (Swagger) |
| Build | Gradle (Kotlin DSL) |

## 아키텍처

### 계층 구조

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer (in/)                  │
│              Controller, Request/Response DTO, Mapper        │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   Application Layer (app/)                   │
│         UseCase, Command DTO, Repository Interface           │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Domain Layer (domain/)                    │
│              Domain Model, Value Object, Domain Service      │
└─────────────────────────────────────────────────────────────┘
                              ▲
                              │ 의존성 역전
                              │
┌─────────────────────────────┴───────────────────────────────┐
│                 Infrastructure Layer (out/)                  │
│           Repository 구현체, JPA Entity, JPA Repository      │
└─────────────────────────────────────────────────────────────┘
```

### Bounded Context

```
                         ┌──────────────┐
                         │   Category   │
                         │   (Generic)  │
                         └──────┬───────┘
                                │ categoryId 참조
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌──────────────┐       ┌──────────────┐       ┌──────────────┐
│     User     │       │    Topic     │       │     Post     │
│  - 회원정보   │       │  - 토픽 관리  │       │  - 게시글    │
│  - 팔로우     │◄─────►│  - 멤버십    │◄─────►│  - 댓글/좋아요│
│  - 관심사     │ event │  - 갤러리    │ event │  - 조회수    │
└──────────────┘       └──────────────┘       └──────────────┘
        │                       │                       │
        │         event         │         event         │
        └───────────────────────┼───────────────────────┘
                                ▼
                       ┌──────────────┐
                       │ Notification │
                       │   - 알림     │
                       └──────────────┘
```

## 주요 설계 결정

### 1. Aggregate Root 분리 기준

> 생명주기가 같더라도 수정/조회 빈도와 데이터양을 고려하여 분리

Topic 삭제 시 TopicPost, TopicMembership도 삭제되어야 하지만, 동시 로드 시 메모리 부담과 DB 락 충돌 위험을 고려해 별도 Aggregate Root로 분리했습니다. 각 Aggregate 간은 ID를 통해 느슨하게 결합합니다.

```java
// Topic과 TopicPost는 별도 Aggregate Root
// 직접 참조 대신 ID로 연결
public class TopicPost {
    private Long topicId;  // Topic 직접 참조 X
    // ...
}
```

### 2. Bounded Context 간 통신 전략

| 상황 | 전략 |
|-----|------|
| 비동기 처리 가능, Eventual Consistency 허용 | Domain Event 발행 |
| 동기적 통신 필요 | 인터페이스를 통한 간접 참조 |

동기적 통신이 필요한 경우 현재는 모놀리식이므로 인터페이스를 통한 간접 참조를 사용하되, MSA 전환 시 API 통신이나 메시지 큐로 쉽게 전환할 수 있도록 설계했습니다.

예시
```java

public interface UserRelationEntryPoint {
    /**
     * return: List of userId
     */
    List<Long> findFollowers(Long userId, Long cursor, int pageSize);
}
```
```java

@RequiredArgsConstructor
@Service
public class UserRelationEntryPointImpl implements UserRelationEntryPoint {
    UserRelationRepository userRelationRepository;

    @Override
    public List<Long> findFollowers(Long userId, Long cursor, int pageSize) {
        ...
    }
```

### 3. UseCase 패턴 도입

초기 단계에서는 Service로 충분했으나, 서비스가 커지면서 다음 문제가 발생했습니다:
- Service 간 경계 모호
- 여러 Repository를 참조하면서 Service가 비대해짐

UseCase 도입으로 비즈니스 의미를 명확히 하고 의존 관계를 분리했습니다.

```java
// Before: 모호한 Service 경계
@Service
public class TopicMembershipService {
    private final TopicRepository topicRepository;  // 다른 Aggregate 직접 참조
    // ...
}

// After: 명확한 UseCase
@Component
public class JoinTopicUseCase {
    // 필요한 Repository만 주입, 비즈니스 의미 명확
}
```

### 4. 패키지 구조: global vs shared 분리

기존 `common` 패키지에 기술 인프라와 도메인 개념이 혼재되어 있던 문제를 해결했습니다.

| 패키지 | 역할 | 예시 |
|-------|------|------|
| `global` | 애플리케이션 전역 기술 인프라 | Config, Exception Handler |
| `shared` | Bounded Context 간 공유 도메인 | Domain Event, 공유 Value Object |
| `boundedcontext` | 각 도메인의 명시적 격리 | user, post, topic |

## 패키지 구조

```
com.findy/
├── boundedcontext/                    # Bounded Context별 도메인
│   ├── user/
│   │   ├── app/                       # Application Layer
│   │   │   ├── dto/                   # Command, Result DTO
│   │   │   ├── interfaces/            # Service Interface
│   │   │   └── usecase/               # UseCase 구현
│   │   ├── domain/                    # Domain Layer
│   │   │   ├── model/                 # Entity, Value Object
│   │   │   └── service/               # Domain Service
│   │   ├── in/rest/                   # Input Adapter (Controller)
│   │   │   ├── request/               # Request DTO
│   │   │   └── response/              # Response DTO
│   │   └── out/repository/            # Output Adapter (Repository)
│   │       ├── entity/                # JPA Entity
│   │       └── jpa/                   # JPA Repository
│   ├── post/
│   ├── topic/
│   ├── category/
│   └── notification/
│
├── global/                            # 전역 기술 인프라
│   ├── config/                        # Spring 설정
│   ├── dto/                           # 공통 DTO
│   ├── entity/                        # BaseTimeEntity
│   ├── event/                         # Event Publisher
│   └── exception/                     # 공통 예외 처리
│
└── shared/                            # BC 간 공유 도메인
    ├── common/domain/                 # 공유 Value Object
    ├── post/event/                    # Post Domain Event
    └── topic/event/                   # Topic Domain Event
```

## 코드 예시

### Domain Model과 JPA Entity 분리

```java
// Domain Model - POJO + 최소한의 Lombok (domain/model/User.java)
@Getter  // 단순 조회용
public class User {
    private final Long id;
    private final UserInfo userInfo;           // Value Object
    private final FollowManager followManager; // 캡슐화 객체
    private final SocialAccount socialAccount; // Value Object

    // 비즈니스 로직은 도메인 내부에서 처리 (Tell, Don't Ask)
    public void follow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new SelfFollowException("자기 자신을 팔로우할 수 없습니다");
        }
        this.followManager.increaseFollowingCount();
        targetUser.followManager.increaseFollowerCount();
    }
}

// JPA Entity - 영속성 담당 (out/repository/entity/UserEntity.java)
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private Long followerCount;
    private Long followingCount;

    // Domain -> Entity
    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.followerCount = user.getFollowerCount();
        // ...
    }

    // Entity -> Domain
    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, nickname, profileImageUrl))
                .followManager(new FollowManager(followerCount, followingCount))
                .socialAccount(socialAccountEntity.toSocialAccount())
                .build();
    }
}
```

### UseCase 패턴

```java
// 하나의 비즈니스 기능 = 하나의 UseCase
@Service
@RequiredArgsConstructor
public class FollowUserUseCase {
    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    @Transactional
    public void execute(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId);
        User followee = userRepository.findById(followeeId);

        follower.follow(followee);  // 도메인 로직 호출

        userRepository.save(follower);
        userRepository.save(followee);
        userRelationRepository.save(new UserRelation(followerId, followeeId));
    }
}
```

### Value Object (불변 객체)

```java
public class PositiveIntegerCounter {
    private final int count;

    public PositiveIntegerCounter increase() {
        return new PositiveIntegerCounter(count + 1);  // 새 객체 반환
    }

    public PositiveIntegerCounter decrease() {
        if (count <= 0) {
            throw new DomainException("카운트는 0 미만이 될 수 없습니다");
        }
        return new PositiveIntegerCounter(count - 1);
    }
}
```

### Repository Interface 분리

```java
// Application Layer - 인터페이스 (app/interfaces/UserRepository.java)
public interface UserRepository {
    User findById(Long id);
    User save(User user);
}

// Infrastructure Layer - 구현체 (out/repository/UserRepositoryImpl.java)
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toUser();
    }
}
```

## 실행 방법

```bash
# 빌드
./gradlew build

# 테스트
./gradlew test

# 애플리케이션 실행
./gradlew bootRun

# API 문서 확인 (실행 후)
# http://localhost:8080/swagger-ui.html
```

## 설계 문서

- [아키텍처 규칙](rules/architecture.md) - 클린 아키텍처, 계층별 DTO 분리
- [도메인 모델 규칙](rules/domain-model-rules.md) - POJO 기반 설계, 불변성, 캡슐화
- [객체지향 생활체조](rules/object-oriented-gymnastics-rules.md) - ThoughtWorks Anthology 9가지 원칙
