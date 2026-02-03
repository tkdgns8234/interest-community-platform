# 아키텍처 규칙

## 핵심 원칙
- DDD(Domain-Driven Design) 및 클린 아키텍처 원칙을 준수합니다.
- 각 계층은 명확한 책임을 가지며, 독립적으로 테스트 가능해야 합니다.
- 의존성 방향은 저수준 모듈에서 고수준 모듈 방향으로 흐릅니다.
- 객체지향 생활체조 9가지 원칙을 준수합니다.

## Aggregate Root 간 통신
1. **Application/Domain Service에서 조율**
   - Bounded Context 간 Service 직접 참조 금지
   - 인터페이스를 통한 간접 참조로 추후 API 통신이나 메시지 큐 전환이 용이하도록 설계
2. **Domain Event 발행**
   - 비동기 처리가 가능하고 Eventual Consistency를 허용하는 경우 활용

## Domain Model과 JPA Entity 분리
- Domain 객체는 순수 비즈니스 로직을 담당하고, JPA Entity는 영속성을 담당합니다.
- Entity는 데이터 저장 방법을 정의하므로 Infrastructure Layer(Repository)에 위치시킵니다.

## UseCase 패턴
비즈니스 로직을 UseCase 단위로 분리하여 단일 책임 원칙을 준수합니다.
- **장점**: Service 비대화 방지, Service 간 의존성 문제 해결, 비즈니스 의미 명확화
- **단점**: 보일러플레이트 코드 증가


---

## 계층별 DTO 분리

### DTO 계층 구분
```
Presentation Layer (Presentation)
├── Request DTO      # API 요청 스펙 (외부 계약)
└── Response DTO     # API 응답 스펙 (외부 계약)

Application Layer
└── Command DTO      # 내부 명령 객체

Domain Layer
└── Entity           # 도메인 모델
```

#### 변환 흐름

```
Request → Command → Domain → Result → Response
  (API)     (App)    (Domain)  (App)    (API)
```

#### 원칙
1. **API DTO는 외부 계약**
  - 내부 로직 변경에 영향 받지 않음

2. **Application DTO는 내부 전달**
  - 계층 간 데이터 전달
  - 자유롭게 변경 가능

3. **Domain은 DTO에 의존하지 않음**
  - 순수 비즈니스 로직
  - DTO 변환은 Application/Presentation 계층에서 (mapper class 활용)
