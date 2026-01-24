## 1. Architecture Rules
- DDD (Domain-Driven Design) 및 클린 아키텍처 원칙을 준수합니다.
- 각 계층은 명확한 책임을 가지고, 독립적으로 배포 가능해야 합니다.
- 의존성 방향은 저수준 모듈에서 고수준 모듈 방향으로 이어져야 합니다.
- Entity와 비즈니스 로직인 Domain 객체를 분리하고, Entity는 데이터 저장 방법을 정의하므로 Repository Layer에 위치시킵니다.
- Service를 UseCase로 대체했을때 현재 서비스는 MVP 수준으로 구현할 예정이기에 득보다 실이 많다고 판단.
  - 장점: Service 로직의 비대 문제 해결, Service 로직 간 의존성 문제 해결
  - 단점: 보일러 플레이트 코드 증가, 코드 복잡성 증가
  - 결론: 현재로서는 Service를 유지하되, 추후 필요시 UseCase로 전환하는 것을 고려.

## 2. 패키지 구조
### 2.1 기본 구조
```
com.findy.{domain}/
├── domain/
│   ├── model/              # Entity, VO, Enum
│   ├── service/            # Domain Service (선택적)
│   └── exception/          # Domain Exception
├── application/
│   └── {feature}/          # 기능별 패키지
│       ├── exception/      # Application Exception
│       ├── interface/      # repository 인터페이스
│       ├── dto/            # Application DTO
│       └── service         # Application Service
├── presentation/
│   │── web/                # REST API 관련 클래스
│   │   └── {feature}/      # 기능별 패키지
│   │        ├── request/ 
│   │        ├── response/
│   │        └── controller
│   └── event/              # 이벤트 핸들러
└── infrastructure/
    └── {external}/         # 외부 시스템별 패키지
```

**원칙**
- Domain 계층: 공유 영역 (모든 기능이 사용)
- Application/Presentation 계층: 기능별 하위 패키지로 분리


## 3. 계층별 DTO 분리
### DTO 계층 구분
```
Presentation Layer (Presentation)
├── Request DTO      # API 요청 스펙 (외부 계약)
└── Response DTO     # API 응답 스펙 (외부 계약)

Application Layer
├── Command DTO      # 내부 명령 객체
└── Result DTO       # 내부 결과 객체

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

2. **Application DTO는 내부 전달**
  - 계층 간 데이터 전달
  - 자유롭게 변경 가능

3. **Domain은 DTO에 의존하지 않음**
  - 순수 비즈니스 로직
  - DTO 변환은 Application/Presentation 계층에서