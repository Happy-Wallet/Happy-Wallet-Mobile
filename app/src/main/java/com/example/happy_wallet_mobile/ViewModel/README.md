# ViewModel Directory

The `ViewModel/` folder contains all ViewModel classes responsible for preparing and managing UI-related data in a lifecycle-conscious way.

## Purpose

- Act as a bridge between the `View` and the `Model` layers
- Hold and manage UI-related data that survives configuration changes (e.g., screen rotations)
- Expose data to the `View` via `LiveData` or other observable data holders
- Handle UI logic and coordinate interactions with the `Repository`

## Responsibilities

- Fetch and transform data from `Repository`
- Expose observable data streams for the UI to observe
- Handle user input commands and update model data accordingly
- Maintain UI state and handle events like loading, success, or error states

## Key Features

- Lifecycle aware — avoids memory leaks by surviving configuration changes
- Does not hold reference to `View` or `Context` directly
- Use `LiveData`, `StateFlow`, or `MutableLiveData` to provide reactive UI updates
- Encapsulates business logic related to UI presentation


## Best Practices

- Keep ViewModel free of Android UI classes (e.g., avoid direct reference to Views)
- Delegate data operations to the `Repository`
- Expose immutable LiveData to `View` while keeping MutableLiveData private
- 
## Benefits

- Decouples UI from data source implementations
- Enables better test coverage by isolating UI logic
- Simplifies handling of configuration changes and state persistence


