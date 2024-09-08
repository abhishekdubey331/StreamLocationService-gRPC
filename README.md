# StreamLocationService

StreamLocationService is a gRPC-based project for providing real-time location updates via simulated GPS coordinates. It mimics a user's movement along a path, making it ideal for applications like tracking, real-time maps, or geolocation services. This project includes a Kotlin server and Android client implementation that leverages Google Maps for displaying locations in real-time.

---

## Features

- **gRPC Server**: Streams real-time location updates to clients.
- **Simulated Location Path**: Generates GPS coordinates for a user "moving" along a predefined path.
- **Android Client**: Displays user locations in real-time using Google Maps and gRPC.
- **Protobuf**: Uses Protocol Buffers for defining the communication structure between client and server.
- **Dependency Injection**: Utilizes Hilt for clean dependency injection.
- **Coroutines**: For handling asynchronous data streams efficiently.

---

## Technologies

- **Kotlin**: Core language for both server and client.
- **gRPC**: For efficient, real-time communication.
- **Protobuf**: Defining messages and services for gRPC.
- **Hilt**: Dependency injection.
- **Google Maps**: Display real-time user locations.
- **Coroutines & Flow**: Handle asynchronous data streams.

---

## Project Structure

📦StreamLocationService ┣ 📂app ┣ 📂data ┣ 📂domain ┣ 📂presentation ┣ 📂proto ┣ 📜README.md ┗ 📜.gitignore


- **`app/`**: Android application including UI components like `MapScreen`.
- **`data/`**: API definitions, repository, and gRPC client for consuming location data.
- **`domain/`**: Contains use cases and business logic for fetching location updates.
- **`presentation/`**: Composable components like `MapScreen` and `CarMarker` for rendering the map and markers.
- **`proto/`**: Protocol buffer (`.proto`) files defining the gRPC services and messages.

---

## Setup and Installation

### Prerequisites

- Android Studio (for the client app)
- Kotlin 1.6+
- gRPC and Protobuf
- Hilt
- JDK 8+ (for the server)

## Running the Server

The server simulates the user’s movement by streaming location data.

## Running the Server

The server simulates the user’s movement by streaming location data.

### Steps to Run the Server:

1. You can run the server directly by executing the `main` method in the `LastKnownLocationServer` class, located in the server code.

2. The server will start on port `9000` by default and begin streaming location data to clients.
