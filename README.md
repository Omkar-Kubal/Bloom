# Bloom 🌸

A comprehensive Android fitness and health tracking application built with Kotlin and Jetpack Compose. Bloom helps users track their workouts, monitor daily steps, calculate TDEE, log their food intake, and achieve their fitness goals through an intuitive and modern UI.

## Features

*   **Authentication & Onboarding**: Seamless user registration and login with Firebase, featuring a personalized 12-step onboarding flow to calculate TDEE and set fitness goals.
*   **Workout Tracking**: Comprehensive workout management including exercise search, active set logging, and AI-powered workout summaries.
*   **Run & Step Tracking**: Background step counting with a dedicated `StepCounterService`, midnight daily reset workers, and step history visualization.
*   **Nutrition Tracking**: Integration with the OpenFoodFacts API to search and track daily food/calorie intake.
*   **Profile & Settings**: Persistent user profiles and settings management using Jetpack DataStore.
*   **Hardware Integration**: Utilizes CameraX for media capture, Activity Recognition for step tracking, and custom Haptic Feedback for enhanced tactile interactions.
*   **Local Storage**: Robust offline support and data caching using Room Database.

## Tech Stack

*   **Language**: Kotlin
*   **UI Toolkit**: Jetpack Compose
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Local Storage**: Room Database & Jetpack DataStore
*   **Backend & Auth**: Firebase Authentication & Google Services
*   **Networking**: Retrofit / OkHttp (OpenFoodFacts API)
*   **Background Processing**: WorkManager & Foreground Services
*   **Hardware APIs**: CameraX, ActivityRecognition, Vibrator API

## Requirements

*   Android Studio (Ladybug or newer recommended)
*   Min SDK: 26 (Android 8.0)
*   Target SDK: 36
*   *Note: A valid `google-services.json` file is required in the `app/` directory for Firebase Authentication to function.*

## Getting Started

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Omkar-Kubal/bloom-v2.git
    ```
2.  **Add Firebase Configuration:**
    Ensure that you have your project's `google-services.json` file placed in the `app/` directory.
3.  **Open in Android Studio:**
    Open the cloned directory in Android Studio.
4.  **Sync and Build:**
    Allow Gradle to sync dependencies and build the project.
5.  **Run the app:**
    Deploy to an Android emulator or a physical device running Android 8.0 or higher.
