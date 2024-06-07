# Library Management System

## Overview
This Java project implements a library management system with functionalities for handling books, book copies, customers, and generating reports. It uses a command-line interface for interaction.

## Prerequisites
Before running the tests, ensure that you have the following installed:
- Java JDK (Java Development Kit) installed on your system.
- JUnit testing framework

## Directory Structure
- `/src/main/java/prototype/Main.java` - The main entry point of the application.
- `/src/main/java/prototype/CommandParser.java` - Handles user input and navigates through the menu.
- `/src/main/java/prototype/cli/CLIRunner.java` - Initiates the command line interface.
- `/src/main/java/prototype/prompt/Prompter.java` - Handles the scanner input.
- `/src/main/java/prototype/commands/Books.java` - Book object
- `/src/main/java/prototype/commands/BookCopies.java` - Book copy object.
- `/src/main/java/prototype/commands/Customers.java` - Customer object.
- `/src/main/java/prototype/commands/Importer.java` - Imports customers/books/book copies using CSV files.
- `/src/main/java/prototype/commands/Manager.java` - Manages book, book copy and customer objects with all type of functionalities.
- `/src/main/java/prototype/commands/Reports.java` - Handles the generation of different reports.
- `/src/main/java/prototype/commands/Exit.java` - Provides functionality to exit the application.

### Test Directory Structure
The test directory (`/test/`) contains the following files:
- `Importer.java`: Consists of unit tests for the import of Customers, Books and BookCopies.
- `ManagerTest.java`: Consists of unit tests for most of the functionality of the system with regards to the customer, the books and book copies.
- `ReportsTest.java`: Consists of unit tests for the methods in the Reports class.


## How to Build, Run, and Test the Application

### Build the Application

1. **Open your terminal or command prompt.**

2. **Navigate to the directory containing the project files.**

3. **Compile the Java files using the following command:**
   ```bash
   ./gradlew build

4. **To run the Gradle use the following command:**
   ```bash
   ./gradlew run

5. **To test the tests use the following command:**
   ```bash
   ./gradlew test

## Calculating Test coverage Record
To calculate the test coverage of our project we can use a tool e.g. JaCoCo which can then display us the coverage of
1. classes
2. methods
3. lines
4. branches

The test coverage itself is calculated on the following formulas (for an example class):
- method coverage = number of executed methods in test cases / number of methods in clas
- line coverage = number of used lines in test cases / number of lines in class
- branch coverage = number of executed branches in test cases / number of unique distinguishable branches in class

(Reference: https://www.baeldung.com/cs/code-coverage)