# End-to-End Weather App

A beautiful, functional, end-to-end weather application built for Android. This app fetches real-time weather and forecasting data utilizing the free Open-Meteo API.

## Features
*   **Search Functionality:** Easily search for current weather by city name using the Open-Meteo Geocoding API.
*   **Detailed Current Weather:** Displays the current temperature, a matching weather icon, and a descriptive label.
*   **Extra Metrics:** View essential weather details at a glance, including Wind Speed, Humidity, "Feels Like" apparent temperature, and UV Index.
*   **24-Hour Forecast:** A horizontally scrolling list allows you to quickly view upcoming temperatures and weather conditions hour-by-hour.
*   **Beautiful UI:** Features an attractive gradient background and clean, contrasting typography.

## Tech Stack
*   **Language:** Java
*   **Networking:** [Retrofit2](https://square.github.io/retrofit/) & Gson Converter (for fetching and parsing API responses)
*   **Image Loading:** [Glide](https://github.com/bumptech/glide) (for displaying weather icons)
*   **UI Components:** `RecyclerView`, `ConstraintLayout`, `GridLayout`, `LinearLayout`

## Setup & Running the App locally in Android Studio

Follow these exact steps to run the application on your computer using Android Studio.

### 1. Prerequisites
*   You must have **Android Studio** installed (preferably the latest version). You can download it from [developer.android.com/studio](https://developer.android.com/studio).
*   Ensure you have Git installed on your system.

### 2. Clone the Repository
1.  Open Android Studio.
2.  On the Welcome screen, click on **"Get from VCS"** (Version Control System). 
    *   *If you already have a project open, go to the top menu and select **File -> New -> Project from Version Control...***
3.  In the URL field, paste the repository link:
    ```
    https://github.com/Dharshansk16/weather-app-MAD.git
    ```
4.  Choose a directory on your computer to save the project and click **Clone**.
5.  Android Studio will now download the code. When prompted to trust the project, click **Trust Project**.

### 3. Sync the Project
1.  Once the project opens, Android Studio will automatically start downloading the required dependencies (Retrofit, Glide, etc.) and building the Gradle model. You will see a progress bar at the bottom right.
2.  Wait for this process to complete. If it doesn't start automatically, go to the top menu and click **File -> Sync Project with Gradle Files** (or click the elephant icon with an arrow in the top toolbar).

### 4. Setting up a Virtual Device (Emulator)
If you don't have a physical Android device, you need to create an emulator.
1.  Click on the **Device Manager** icon in the right-hand toolbar (or go to **Tools -> Device Manager**).
2.  Click the **"+" (Create device)** button.
3.  Choose a phone hardware profile (e.g., Pixel 7) and click **Next**.
4.  Select a System Image (preferably the latest stable API level, e.g., API 34 or 35). You may need to click the download arrow next to it first. Click **Next**.
5.  Give your device a name and click **Finish**.

### 5. Running the App
1.  Look at the top toolbar in Android Studio. You should see a drop-down menu for selecting the target device. Select the Emulator you just created (or your physical device if plugged in via USB/WiFi debugging).
2.  Click the **Green "Run" button (Play icon)** next to the device drop-down (or press `Shift + F10` / `Control + R`).
3.  Android Studio will compile the app and install it on the selected device. 
4.  The Weather App will launch automatically! Enter a city in the search bar and hit search.

## API Usage
This app uses [Open-Meteo](https://open-meteo.com/), which is completely free for non-commercial use and **does not require an API key**. 
*   Geocoding API: `https://geocoding-api.open-meteo.com/`
*   Weather API: `https://api.open-meteo.com/`
