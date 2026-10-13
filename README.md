# To Do List (Android)

A small Kotlin Android to-do app for adding and deleting text tasks. It saves the list to app-local storage and reloads it when the app starts.

## What the code does

- `MainActivity.kt` handles the task list, add action, and delete confirmation.
- `FileHelper.kt` reads and writes the stored list.
- The UI uses an XML layout in `app/src/main/res/layout/activity_main.xml`.

## Build and test

Open the project in Android Studio and run the `app` configuration, or use `./gradlew assembleDebug` (`gradlew.bat assembleDebug` on Windows). `./gradlew testDebugUnitTest` runs the included template unit test; there are no feature-specific automated tests yet.

Data is stored locally on the device. This repository does not include accounts, cloud sync, reminders, or a backend.
