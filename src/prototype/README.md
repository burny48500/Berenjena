# Library Management System

## Overview
This Java project implements a library management system with functionalities for handling books, book copies, customers, and generating reports. It uses a command-line interface for interaction.

## Prerequisites
- Java JDK (Java Development Kit) installed on your system.

## Directory Structure
- `/Main.java` - The main entry point of the application.
- `/CommandParser.java` - Handles user input and navigates through the menu.
- `/cli/CLIRunner.java` - Initiate command line interface
- `/prompt/Prompter.java` - Handles the scanner input
- `/commands/Books.java`, `BookCopies.java` - Manage book and book copy related functionalities.
- `/commands/Customers.java` - Manage customer-related functionalities.
- `/commands/Reports.java` - Handles the generation of different reports.
- `/commands/Exit.java` - Provides functionality to exit the application.

## Compilation Instructions
1. Open your terminal or command prompt.
2. Navigate to the directory containing the project files.
3. Compile the Java files using the following command:
   ```bash
   javac *.java
   ```
## Run application
To run the application, use the following command after compiling:
   ```bash
   java Main
   ```
## Functionality
The application provides functionalities through a menu-driven interface:
1. Books Menu: Search, Borrow, Return, Delete, and Import books and book copies.
2. Customer Menu: Create, Modify, Delete, and Import customer records.
3. Report Menu: Generate reports on all books, borrowed, not borrowed, and customers.

## Running Tests

### Prerequisites
Before running the tests, ensure that you have the following installed:
- Java JDK (Java Development Kit)
- JUnit testing framework

(If you're using Gradle, JUnit might be included automatically as part of your project's dependencies. However, if you're running the tests manually or outside of a build system, you might need to install JUnit separately.)


### Test Directory Structure
The test directory (`/test/`) contains the following files:
- `CustomerTest.java`: Contains unit tests for customer deletion functionalities.
- `BookDeletionTest.java`: Includes unit tests for book deletion functionalities.
- `BookCopyDeletionTest.java`: Consists of unit tests for book copy deletion functionalities.

## Test Execution
To execute the unit tests using Gradle, follow these steps after compiling the Java files:
1. Open your terminal or command prompt.
2. Navigate to the root directory of your project.
3. Run the following Gradle command to execute the tests:
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
- branche coverage = number of executed branches in test cases / number of unique distinguishable branches in class

(Reference: https://www.baeldung.com/cs/code-coverage)
(Disclaimer: somehow the test code coverage with our repository in IntelliJ only worked when the test classes themselves also were in the commands directory)