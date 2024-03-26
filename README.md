# About the app

This app is capable of listing all events provided my Ticketmaster's Discovery API.

### The app was built using the following technologies: 
- Kotlin
- MVVM
- Room
- Dependency Injection
- Coroutines
- Retrofit
- Glide
- Unit tests using JUnit and MockK

### Features
- List all events from Ticketmaster's Discovery API
- Open event's details on a browser
- Loading with pagination to deliver a smooth UX
- Search events by Keyword
- Search events by City
- Save events in a local database
- Handle network exceptions

### How to run 
1. Clone the project from this repository
2. Open the project on your android studio
3. Put your *API_KEY* value on app/build.gradle (not ideal, thinking about ease of build)
4. Make sure your gradle and kotlin version are up-to-date and sync the project
5. Run the project on an emulator or virtual device.

### Known issues
- [x] The app does not paginate when it has search parameters set [Fixed] 
- [x] The app is not using Dependency Injection [Fixed]
- [ ] The app does not get saved events from database in case of network error yet (working on it)
