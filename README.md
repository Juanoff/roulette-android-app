# Roulette

The roulette wheel contains dynamically generated sectors with different colors and values. During wheel rotation the application calculates the resulting sector and displays the selected value after animation completion.

<img src="screenshots/1.jpg" width="250">  <img src="screenshots/2.jpg" width="250">

## Features

- Configurable number of sectors
- Random spin generation
- Winner sector calculation
- Smooth deceleration animation
- Adaptive layouts for portrait and landscape orientations
- Custom wheel rendering via Canvas API

## Tech stack

- Kotlin
- Jetpack Compose + Material 3
- MVVM, StateFlow, Unidirectional Data Flow
- Hilt
- Coroutines + Flow
- Animatable API

## Requirements

- Android SDK 26+
- JDK 21
- Android Studio

## Clone Repository

```bash
git clone https://github.com/Juanoff/roulette-android-app.git
```

## Open Project

Open the project in Android Studio.

## Run Application

Run the application on:

- Android Emulator;
- physical Android device.
