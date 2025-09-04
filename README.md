
This mobile application that lists countries and their details using GraphQL.
Built with MVVM Clean Architecture in Kotlin, leveraging Jetpack Compose for UI and Hilt for dependency injection.

**Tech Stack**

Language: Kotlin

Architecture: MVVM + Clean Architecture

UI: Jetpack Compose

Navigation: Jetpack Navigation

Remote API: GraphQL

Database: RoomDB

Dependency Injection: Hilt (Dagger)

Asynchronous Ops: Coroutines & Flow

**Architecture**
The app follows MVVM Clean Architecture to ensure scalability, testability, and separation of concerns.

Layers:

Presentation Layer (UI)

Built with Jetpack Compose.

Uses ViewModel for UI state management.

Observes data using Flow.

Domain Layer

Contains business logic.

Defines use cases (interactors).

Independent of frameworks.

Data Layer

Responsible for fetching and caching data.

Contains repositories and data sources.

Sources:

Remote → GraphQL API.

Local → RoomDB.
