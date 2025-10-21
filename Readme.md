# An intelligent Android expense tracking application built with Kotlin and Jetpack Compose, leveraging the power of Generative AI (Gemini), Camerax & MLToolkit to simplify and automate expense management.

## ✨ Features

- Manual & Autodetect Expense Entry: Quickly add expenses with title, amount, and category.
- AI-Powered Category Suggestion: Automatically suggests a relevant category based on the expense title, powered by the Gemini API.
- Receipt Scanning (OCR): Use your device's camera to scan a receipt. The app extracts the merchant name, total amount, and date, and automatically fills in the details for you.
- Clean & Modern UI: A beautiful and intuitive user interface built entirely with Jetpack Compose.
- Data Visualization: View a summary of your spending with a clean pie chart that visualizes expenses by category.
- Modular Architecture: Built using a multi-module architecture for better separation of concerns, scalability, and maintainability.

## 🛠️ Tech Stack & Architecture
This project follows modern Android development practices and showcases a robust architecture.
- Kotlin: First-party language for Android development.
- Jetpack Compose: Modern declarative UI toolkit for building native Android UI.
- MVVM (Model-View-ViewModel): A robust architecture pattern to separate UI from business logic.
- Hilt: For dependency injection, making the codebase more modular and testable.
- Kotlin Coroutines & Flow: For managing asynchronous operations and handling streams of data.
- Ktor: A type-safe HTTP client for making API calls to the Gemini backend.
- Room: For local database storage, providing offline access to expense data.
- Jetpack Navigation: For handling navigation between different screens in the app.
- Kotlinx.serialization: For parsing JSON data from the API.
- Gradle Version Catalog (libs.versions.toml): For centralized dependency management.
- Generative AI (Gemini API): Used for intelligent category suggestions and extracting details from receipts.
- Multi-module Architecture:◦app: The main application module.
  - features: Contains individual feature modules (expensehome, addexpense, etc.).
  - data: Handles data sources (local database and remote API).
  - repository: Abstracts the data layer from the ViewModels.
  - navigation: Manages navigation routes and graphs.
  - design: A shared module for UI components, themes, and design system elements.

## 🚀 Getting Started
To build and run the project, you need to have Android Studio and a configured Gemini API key.
Prerequisites
- Android Studio (latest version recommended)
- A Google Gemini API Key.
- To Add your Gemini API Key:
  - Open the local.properties file in the root of the project. If it doesn't exist, create it.
  - Add your API key to the file like this:
    local.properties -> GEMINI_API_KEY="YOUR_API_KEY_HERE"
      
## App Screen shots 

<img width="200" alt="Screenshot_20251021-171653" src="https://github.com/user-attachments/assets/755a92c3-e21a-4b41-b538-9b092f743a0b" />
<img width="200" alt="Screenshot_20251021-171733" src="https://github.com/user-attachments/assets/f2ba12d8-2ec9-421f-b24e-60b6776031d6" />
<img width="200" alt="Screenshot_20251021-171916" src="https://github.com/user-attachments/assets/fe0ea244-e466-4a9b-971f-81a891c66a73" />
<img width="200" alt="Screenshot_20251021-172001" src="https://github.com/user-attachments/assets/7b32e403-9150-4940-8f56-eac7f124bfd9" />
