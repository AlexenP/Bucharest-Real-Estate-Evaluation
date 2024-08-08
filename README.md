# Real Estate Report Generator

## Description

Real Estate Report Generator is a web application designed for generating real estate reports, allowing users to evaluate properties and visualize market price distribution. The application is built using a modern tech stack and is optimized to offer an interactive and intuitive user experience.

## Features

- Property evaluation based on user-input information.
- Price distribution visualization through interactive bar charts.
- User and role management via an admin panel.
- Storage of geographical data using the GeoJSON format.

## Technologies Used

### Backend
- **Java Spring Boot**: A robust framework for developing enterprise-level applications.
- **Hibernate (JPA)**: An ORM for interacting with the MySQL database.

### Frontend
- **Thymeleaf**: A template engine for rendering HTML in Java.
- **HTML/CSS/JavaScript**: Standard technologies for the user interface.
- **Chart.js**: A JavaScript library for data visualization in charts.

### Database
- **MySQL**: A relational database management system.

### Other Tools
- **GeoJSON**: A format for geographic data.
- **GitHub**: A platform for source code management.

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/real-estate-report-generator.git
    ```

2. **Import the project into your preferred IDE**.

3. **Configure the database**:
   - Create a MySQL database.
   - Update `application.properties` or `application.yml` with your database details.

4. **Run the application**:
   - Start the project from the IDE or using Maven:
     ```bash
     mvn spring-boot:run
     ```

5. **Access the application**:
   - The application will be available at `http://localhost:8080`.

## Usage

- **Evaluate a Property**: Access the evaluation page, enter property details, and generate a report.
- **View Reports**: Interactive charts allow you to analyze market price distribution.
- **User Management**: Manage user accounts and roles via the admin panel.

## Contributions

Contributions are welcome! Please open a pull request or report issues on GitHub.

