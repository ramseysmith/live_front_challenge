# Live Front Challenge - The Wire Character Viewer

This is an Android app written in Kotlin that demonstrates fetching and displaying data from a RESTful Web API. The app consists of a list view and a detail view, allowing users to browse and explore character information.

## Features

The app provides the following features:

- List View: Displays a vertically scrollable list of character names.
- Detail View: Shows detailed information about a selected character, including the character's image, title, and description.

## Technologies Used

The app is built using the following technologies:

- Kotlin: The programming language used for developing the Android app.
- Android Jetpack: A collection of Android libraries and tools that help developers write high-quality apps more easily.
- Compose: The modern UI toolkit for building native Android user interfaces.
- Retrofit: A type-safe HTTP client for making network requests to the RESTful Web API.
- Coroutines: Kotlin's native solution for handling asynchronous programming and concurrency.
- Hilt: A dependency injection library for Android.
- ViewModel: A class designed to store and manage UI-related data in a lifecycle-aware way.
- Flow: An observable data holder class that allows components to be notified of data changes.
- Coil: A fast and efficient image loading library for Android.

## Installation

To build and run the app locally, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.

## Usage

Upon launching the app, you will be presented with a list of character names. You can scroll through the list. Tap on a character to view more details, including the character's image, title, and description.

## Screenshots
![List View](https://github.com/ramseysmith/live_front_challenge/assets/136852452/a3e7ce60-ebda-4795-8028-ef8ddacd2455)
![Detail View](https://github.com/ramseysmith/live_front_challenge/assets/136852452/f03c966f-054f-46ef-8933-e6eef343e29e)

## API Documentation

The app fetches data from the RESTful Web API at [http://api.duckduckgo.com/?q=the+wire+characters&format=json]. The API provides a JSON response containing character information, including the character's name, description, and image URL.

For the image in the detail view, the app uses the URL provided in the "Icon" field of the API JSON response. If the image URL is blank or missing, a placeholder image is displayed instead.

## Credits

This app was developed as part of the Live Front Challenge. It was created by Ramsey Smith and serves as a showcase of their skills and knowledge in Android app development using Kotlin, Compose, and other relevant technologies.

Feel free to explore the code and customize it to suit your needs. Enjoy using the app!
