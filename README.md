
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
com.example.countries
│
├── data
│   ├── local (RoomDB)
│   ├── remote (GraphQL API)
│   └── repository
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── presentation
│   ├── ui (Jetpack Compose screens)
│   └── viewmodel
│
└── di (Hilt Modules)

