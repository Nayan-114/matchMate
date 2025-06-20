##MatchMate

A matrimonial-style Android app built with Kotlin, Jetpack Compose, Retrofit, and Room.

Features

Match Algorithm: Scores (0–100) based on age proximity: 100 - 2×|userAge - 30|.

Offline Support: Cached profiles in Room; the app functions even without an internet connection.

Flaky Network Simulation: 30% request failure rate with 3× retry logic.

Accept/Decline: Persist user choices locally; UI reflects selection.

Connectivity Snackbar: Shows online/offline status.

Architecture

MVVM: UserViewModel + UserRepository + Compose UI.

Networking: Retrofit + OkHttp interceptor for failures.

Local Storage: Room database (UserEntity) with a reactive Flow.

Future Enhancement

If given more time, I would implement in-app messaging and real-time notifications so that once two users both accept a match, they can chat directly within the app. This would involve:

*A lightweight messaging backend (e.g., Firebase Realtime Database or WebSocket server)

*Push notifications for new messages
