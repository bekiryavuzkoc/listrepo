# 📱 Project Details

This project was developed as part of a case study to demonstrate modern Android development practices, clean architecture principles, and testable code structure.

The application presents the same business logic using two different UI approaches:

- XML + ViewBinding
- Jetpack Compose

On the first screen, users can navigate to either the XML-based implementation or the Compose-based implementation.

---

# 🏗 Architecture

The project follows **Clean Architecture** principles and is separated into three main layers:

- presentation
- domain
- data

## Domain Layer
- UseCases
- Domain models
- Business logic

## Data Layer
- RemoteDataSource
- Repository implementations
- DTO → Domain mapping
- Network error handling

## Presentation Layer
- XML screens (ViewBinding)
- Compose screens
- ViewModels
- Intent/Event based state management

---

# State Management

- StateFlow is used for UI state

- Immutable UI state model

- MutableSharedFlow is used for one-time events (navigation)

# 🔄 Pull-To-Refresh

- Both UI implementations support pull-to-refresh:

- XML: SwipeRefreshLayout

- Compose: PullRefresh

- Refresh actions are handled via Intent and processed inside the ViewModel.

# 🧪 Testing

## Unit tests were written for:

- Repository

- UseCase

- ViewModel

## Technologies used for testing:

- MockK

- Turbine

- kotlinx-coroutines-test

- JUnit

## Tests cover:

- Success and error scenarios

- State emission verification

- Event emission verification

# 🛠 Tech Stack

- Kotlin

- Coroutines

- Flow / StateFlow

- Hilt

- Retrofit

- ViewBinding

- Jetpack Compose

- MockK

- Turbine

- Clean Architecture