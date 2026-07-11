# Expense Tracker Application

A comprehensive, full-stack application for managing personal finances. This project allows users to track their daily transactions, categorize expenses, view visual analytics, generate PDF reports, and receive AI-driven insights. It consists of a robust Spring Boot backend and a dynamic Angular frontend.

## 🏗️ System Architecture

The project is split into a decoupled frontend and backend:
* **`expense-tracker-backend/`**: A RESTful API built with Java and Spring Boot.
* **`expense-tracker-frontend/`**: A Single Page Application (SPA) built with Angular.

---

## ⚙️ Backend Implementation (`expense-tracker-backend`)

The backend serves as the core engine, providing secure endpoints, interacting with the database, and integrating third-party libraries for enhanced features.

### Key Features & Technologies:
* **Framework**: Spring Boot 4 (Java 21)
* **Database**: MySQL (via Spring JDBC)
* **Security & Authentication**: Implemented via Spring Security (`SecurityConfig.java`) to secure endpoints and likely provide JWT or session-based authentication for users.
* **Email Automation**: Uses `spring-boot-starter-mail` (with dedicated logic in the `Automation` package) to send notifications, alerts, or summaries to users.
* **Report Generation**: Integrates `OpenPDF (LibrePDF)` to generate downloadable, formatted PDF reports of user transactions and expenses.
* **AI Insights**: Integrates **Spring AI with Google GenAI** (`spring-ai-starter-model-google-genai`) to provide intelligent categorizations or spending insights based on user data.
* **Architecture**: Clean, layered structure comprising `Controllers`, `Services`, `Repositories`, `Models`, `DTOs`, `Mappers`, and custom `Exceptions`.

---

## 💻 Frontend Implementation (`expense-tracker-frontend`)

The frontend is an intuitive web interface that allows users to seamlessly interact with their financial data.

### Key Features & Technologies:
* **Framework**: Angular 16 (TypeScript)
* **State & Routing**: Uses Angular's native Routing and Reactive Forms for handling user inputs and navigation.
* **Visual Dashboards**: Integrates **Chart.js** via a dedicated `chart` service to render visual analytics, helping users track where their money is going.
* **Component Structure**: 
  * `auth`: Handles user login and registration flows.
  * `dashboard`: The main hub displaying summaries and charts.
  * `transaction`: Interface for adding, viewing, and managing income/expenses.
  * `category`: Interface for organizing expenses into manageable buckets.
  * `navbar`: Global navigation layout.

---

## 🚀 Getting Started

### Prerequisites
* **Java 21** & **Gradle** (for the backend)
* **Node.js** & **Angular CLI v16** (for the frontend)
* **MySQL Server** 

### Setting up the Database
1. Ensure MySQL is running.
2. Execute the provided SQL scripts (`ExpenseTracker.sql` and `queries.sql`) in the root directory to initialize the database schema and tables.

### Running the Backend
1. Navigate to `expense-tracker-backend`.
2. Configure your `application.properties` or `application.yml` with your MySQL credentials, Email SMTP settings, and Google GenAI API keys.
3. Start the application:
   ```bash
   ./gradlew bootRun
   ```

### Running the Frontend
1. Navigate to `expense-tracker-frontend`.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   ng serve
   ```
4. Open your browser and navigate to `http://localhost:4200/`.
