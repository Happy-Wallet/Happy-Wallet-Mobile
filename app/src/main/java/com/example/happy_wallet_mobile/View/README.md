# View Directory

The `View/` folder contains all the UI-related components of the application. This includes `Activities`, `Fragments`, custom views, and UI event handling logic.

## Purpose

- Display data to users using `TextView`, `EditText`, `RecyclerView`, etc.
- Observe changes from `ViewModel` and update the UI accordingly
- Handle user interactions and forward them to the `ViewModel`

## Responsibilities

- Bind data from the `ViewModel` to the UI
- Handle lifecycle events (`onCreate`, `onResume`, etc.)
- Keep UI logic only – no business logic or data processing

## Key Components

- `Activities` – entry points for UI screens, e.g., `SignInActivity`
- `Adapters` – bind lists of data to `RecyclerView` or similar views

## Best Practices

- Use `ViewModel` for all data and state management
- Observe `LiveData` from `ViewModel` to reflect real-time updates
- Do not fetch data or implement business logic directly in the view
- Minimize direct access to shared resources like preferences or database
- 
## Benefits

- Clear separation between UI and logic
- Easy to test `ViewModel` without UI dependencies
- Reusable, modular UI components

