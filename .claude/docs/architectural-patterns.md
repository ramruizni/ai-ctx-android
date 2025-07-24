# Architectural Patterns

## Module Organization

### Features Structure (Screen Flows)
UI-focused features organized by screen flows:
```
features/
└── SCREEN_FLOW_NAME/      # e.g., login, notifications, profile
    ├── view/              # Composable screens and UI components
    └── viewmodel/         # ViewModels and UI state management
```

### Domain Entities Structure (Data Models)
Core business entities organized by data domains:
```
ENTITY_NAME/               # e.g., user, notification, task
├── datasource/            # Room DAOs, converters, DTOs
├── domain/               # Models, use cases, repository interfaces
└── infrastructure/       # Repository implementations
```

## Navigation Structure (Screen Flows)
Navigation is organized by screen flows, not data models:
```
navigation/
├── NavigationHost.kt
├── RootGraphRoute.kt
└── SCREEN_FLOW_NAME/      # e.g., login/, notifications/, profile/
    ├── [Flow]Graph.kt
    ├── [Flow]Navigator.kt
    └── routes/
        ├── [Flow]GraphRoute.kt
        └── [Screen]Route.kt
```

### Examples:
- **Data Entity**: `user/` (User, UserDbDto, UserDao, etc.)
- **Screen Flow**: `features/login/` + `navigation/login/`
- **Data Entity**: `notification/` (Notification, NotificationDbDto, etc.)  
- **Screen Flow**: `features/notifications/` + `navigation/notifications/`

## Data Layer Architecture

### Model vs DbDto Pattern
- **Model**: Pure business entity (e.g., `User`, `Notification`)
- **DbDto**: Database-specific entity with Room annotations (e.g., `UserDbDto`, `NotificationDbDto`)

## Module Structure Details

### Domain Entity Module (Data-focused)
```
user/                      # Data entity
├── datasource/           # Gradle module (:user:datasource)
│   ├── daos/             # UserDao
│   ├── dbdtos/          # UserDbDto
│   ├── converters/      # Type converters
│   └── UsersDataSourceImpl.kt
├── domain/              # Gradle module (:user:domain)
│   ├── models/          # User (pure model)
│   ├── usecases/        # GetUserUseCase, etc.
│   └── UsersRepository.kt
└── infrastructure/      # Gradle module (:user:infrastructure)
    ├── UsersDataSource.kt
    └── UsersRepositoryImpl.kt
```

### Module Dependencies
```
:user:datasource 
  ↳ depends on :user:domain
  ↳ depends on :user:infrastructure
  ↳ includes Room dependencies

:user:infrastructure
  ↳ depends on :user:domain

:user:domain
  ↳ pure Kotlin (no Android dependencies)

:features:profile:view
  ↳ depends on :user:domain
  ↳ depends on :features:profile:viewmodel

:features:profile:viewmodel  
  ↳ depends on :user:domain
```

### Feature Module (Screen flow-focused)
```
features/login/            # Screen flow
├── view/                 # Gradle module (:features:login:view)
│   ├── LoginScreen.kt
│   ├── RegisterScreen.kt
│   └── ILoginNavigator.kt
└── viewmodel/           # Gradle module (:features:login:viewmodel)
    ├── LoginViewModel.kt
    └── RegisterViewModel.kt
```

### Navigation (Screen flow-focused)
```
navigation/login/          # Matches screen flow name
├── LoginGraph.kt
├── LoginNavigator.kt
└── routes/
    ├── LoginGraphRoute.kt
    ├── LoginRoute.kt
    └── RegisterRoute.kt
```

## Relationship Examples
- **user/** entity can be used by **features/login/**, **features/profile/**, etc.
- **notification/** entity can be used by **features/notifications/**, **features/settings/**, etc.
- Navigation follows screen flow names, not entity names

## Data Flow
**Screen Flow** → **ViewModel** → **UseCase** → **Repository** → **DataSource** → **DAO**

The screen flows consume domain entities through use cases, but navigation is organized independently by user journey, not data structure.