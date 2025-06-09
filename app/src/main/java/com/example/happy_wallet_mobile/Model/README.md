# Model Directory

The `Model/` folder contains the data classes that define the structure of the application's core entities.
These models are used across the app to represent both local and remote data.

## Purpose

- Represent business entities like `User`, `Transaction`, `ToDoItem`, etc.
- Serve as Data Transfer Objects (DTOs) between ViewModel, Repository, Remote, and Local layers
- Help unify data formats across different sources (API, Database, UI)

## Responsibilities

- Hold raw data without containing any UI or business logic
- Be easily serializable (e.g., for JSON conversion or database storage)
- Work with both **Remote** (API) and **Local** (Database) layers

## Guidelines

- Avoid implementing logic; models should be **data-only**
- Separate models if necessary:

## Benefits

- Improves code readability and maintainability
- Reduces coupling between components
- Makes the app more scalable and testable

