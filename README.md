# Library Management System

Berenjena is a command-line based library management system implemented in Java that helps libraries manage their books, book copies, and customers.

## Project Description

This Library Management System is designed to help libraries transition from Excel-based management to a specialized software solution. The system handles core library operations including book management, customer management, and borrowing processes.

## Features

- Book Management
  - Import books via CSV
  - Delete books by ISBN
  - Search books by ISBN, title, or author
  
- Book Copy Management
  - Import book copies via CSV
  - Delete book copies by ID
  - Track borrowing status
  
- Customer Management
  - Import customers via CSV
  - Delete customers by ID
  - Track borrowing limits and overdue fees
  
- Borrowing Operations
  - Borrow books (with automatic validation)
  - Return books (with overdue fee calculation)
  - Enforce borrowing limits (max 5 books per customer)
  
- Reporting
  - List all books
  - List borrowed book copies
  - List non-borrowed book copies
  - List all customers
  - List customer's borrowed books
  - List book copies per publisher

## Prerequisites

- Java SE 17
- Maven or Gradle (depending on chosen build tool)

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
