# SauceDemo Selenium Java POC

A simple, robust Selenium Java automation framework for testing the [SauceDemo](https://www.saucedemo.com/) website. This project uses Cucumber for BDD-style testing and TestNG as the test runner.

## 🚀 Status CI Integration
[![Selenium Java CI](https://github.com/fawziridwan/saucedemo-web/actions/workflows/ci.yml/badge.svg)](https://github.com/fawziridwan/saucedemo-web/actions/workflows/ci.yml)

## 🛠️ Minimum Requirements
- **Java Development Kit (JDK):** 11 or higher
- **Apache Maven:** 3.6+
- **Google Chrome:** Latest stable version
- **IDE (Recommended):** IntelliJ IDEA or VS Code with Java extensions

## 📥 How to Clone the Project
Open your terminal and run the following command to clone the repository:

```bash
git clone https://github.com/fawziridwan/saucedemo-web.git
cd saucedemo-web
```

## 🏃 How to Run
You can run the tests directly from the terminal using Maven:

### Run All Tests
```bash
mvn test
```

### Run Tests in Headless Mode
To run tests without opening a browser window (useful for CI environments):
```bash
mvn test -Dheadless=true
```

### View Reports
After running the tests, reports are generated in the following locations:
- **Cucumber Report:** `target/cucumber-reports.html` (if configured)
- **TestNG Reports:** `target/surefire-reports/index.html`

---
*Developed with the goal of creating a clean and scalable automation architecture.*
