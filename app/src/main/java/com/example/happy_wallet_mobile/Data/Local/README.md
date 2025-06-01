# Local Data Layer

This directory contains components responsible for **storing and managing local data** on the device.  
It is part of the `data` layer in the MVVM architecture.

## Purpose

The `local/` package is responsible for:
- Persisting user session data (e.g., access token, username)
- Caching user-related information
- Managing local databases or preferences
- Providing local data access to repositories

## Components

### 1. `UserPreferences.java`
- Save and retrieve user credentials
- Store authentication token
- Handle session management

## Best Practices

- Avoid placing business logic here. Use this layer for storage only.
- Interact with this layer through a repository
- Keep data format simple and secure.
