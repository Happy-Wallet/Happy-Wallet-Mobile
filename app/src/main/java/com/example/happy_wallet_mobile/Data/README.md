# Data Layer

The `data/` directory serves as the central location for all **data-related logic** in the application. 
It contains the code responsible for accessing, managing, and providing data to the rest of the app
(primarily the Repository and ViewModel layers).

## Structure

data/
│
├── local/ # Local data sources (e.g. Room database, SharedPreferences)
├── remote/ # Remote data sources (e.g. REST APIs, Firebase)
└── repository/ # Repositories that combine local and remote data sources


## Responsibilities

- Handle **data retrieval**, **storage**, and **synchronization**
- Abstract away the complexity of data sources
- Provide a **single source of truth** for business logic and UI layers

## Key Principles

- **Separation of concerns**: The data layer knows where and how to get the data; ViewModels should not.
- **Single responsibility**: Each sub-package should only handle one type of data source (local or remote).
- **Encapsulation**: ViewModels and UI components interact only with repositories, not data sources directly.

## Benefits

- Simplifies the ViewModel layer
- Makes the app easier to test and maintain
- Supports advanced features like offline caching and data syncing
- 