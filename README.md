# Library Management System

## Overview
This Java project implements a library management system with functionalities for handling books, book copies, customers, and generating reports. It uses a command-line interface for interaction.

## Prerequisites
- Java JDK (Java Development Kit) installed on your system.

## Directory Structure
- `Main.java` - The main entry point of the application.
- `CommandParser.java` - Handles user input and navigates through the menu.
- `Books.java`, `BookCopies.java` - Manage book and book copy related functionalities.
- `Customers.java` - Manage customer-related functionalities.
- `Reports.java` - Handles the generation of different reports.
- `Exit.java` - Provides functionality to exit the application.

## Compilation Instructions
1. Open your terminal or command prompt.
2. Navigate to the directory containing the project files.
3. Compile the Java files using the following command:
   ```bash
   javac *.java
   
## Run application
To run the application, use the following command after compiling:
   ```bash
   java Main

## Functionality
The application provides functionalities through a menu-driven interface:
1. Books Menu: Search, Borrow, Return, Delete, and Import books and book copies.
2. Customer Menu: Create, Modify, Delete, and Import customer records.
3. Report Menu: Generate reports on all books, borrowed, not borrowed, and customers.
