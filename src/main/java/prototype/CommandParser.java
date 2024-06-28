package prototype;

import prototype.commands.*;
import prototype.prompt.Prompter;

/**
 * The `CommandParser` class handles the parsing and execution of commands from the user.
 */
public class CommandParser {
    private final Prompter prompter = new Prompter();
    private Importer importer = new Importer();
    private Manager manager = new Manager();
    private Customer customer;
    private Reports reports = new Reports();

    /**
     * Creates customers, books, and book copies.
     */
    public void creation() {
        manager.creationCustomers();
        manager.creationBooks();
        manager.creationBookCopies();
    }

    /**
     * Displays the initial menu and processes user input.
     *
     * @param text whether to display the menu
     */
    public void initialMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Initial Menu DEVELOPMENT:
                    |0|: Books
                    |1|: Books Copies
                    |2|: Customers
                    |3|: Reporting
                    |4|: Exit
                    """);
        }
        String answer = prompter.nextInput().strip();
        switch (answer) {
            case "0":
                booksMenu(true);
                break;
            case "1":
                booksCopiesMenu();
                break;
            case "2":
                customersMenu(true);
                break;
            case "3":
                reportMenu(true);
                break;
            case "4":
                prompter.close();
                Exit.close();
            default:
                System.out.println("Wrong input. Try again.");
                initialMenu(false);
        }
    }

    /**
     * Displays the books menu and processes user input.
     *
     * @param text whether to display the menu
     */
    private void booksMenu(boolean text) {
        if (text) {
            System.out.println("Book Menu:\n" + """
                    |0|: Import Books
                    |1|: Delete
                    |2|: Back to Initial Menu
                    """);
        }
        String answer = prompter.nextInput().strip();
        String isbn;
        switch (answer) {
            case "0":
                importer.selectFile();
                break;
            case "1":
                System.out.println("Enter the ISBN of the book:");
                isbn = prompter.nextInput();
                manager.deleteBook(isbn);
                break;
            case "2":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                booksMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }

    /**
     * Displays the search menu and processes user input.
     *
     * @param text whether to display the menu
     */
    private void searchMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Search Book Copy by:
                    |0|: Title
                    |1|: Author
                    |2|: ISBN
                    |3|: Back to Books Menu
                    """);
        }
        String answer = prompter.nextInput();
        switch (answer) {
            case "0":
                System.out.println("Enter a title:");
                String title = prompter.nextInput();
                manager.searchByTitle(title);
                break;
            case "1":
                System.out.println("Enter an author:");
                String author = prompter.nextInput();
                manager.searchByAuthor(author);
                break;
            case "2":
                System.out.println("Enter a ISBN:");
                String isbn = prompter.nextInput();
                manager.searchByISBN(isbn);
                break;
            case "3":
                booksMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                searchMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }

    /**
     * Displays the book copies menu and processes user input.
     */
    private void booksCopiesMenu() {
        System.out.println("Book Copies Menu:\n" + """
                |0|: Borrow
                |1|: Return
                |2|: Search
                |3|: Import
                |4|: Delete
                |5|: Back to Initial Menu
                """);
        String answer = prompter.nextInput();
        int userId, copyId;
        switch (answer) {
            case "0":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                manager.borrowBookCopy(copyId, userId);
                break;
            case "1":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                manager.returnBookCopy(copyId, userId);
                break;
            case "2":
                searchMenu(true);
                break;
            case "3":
                importer.importBookCopy();
                break;
            case "4":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                manager.deleteBookCopy(copyId);
                break;
            case "5":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                booksMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }

    /**
     * Displays the customers menu and processes user input.
     *
     * @param text whether to display the menu
     */
    private void customersMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Select an action:
                    |0|: Modify
                    |1|: Create
                    |2|: Delete
                    |3|: Import Customer file
                    |4|: Back to Customer Menu
                    """);
        }
        String answer = prompter.nextInput();
        String mail, phoneNumber;
        int userId;
        switch (answer) {
            case "0":
                modifyCustomers(true);
                break;
            case "1":
                System.out.println("Enter the first name of the customer:");
                String firstName = prompter.nextInput();
                System.out.println("Enter the name of the customer:");
                String name = prompter.nextInput();
                System.out.println("Enter the email of the customer:");
                mail = prompter.nextInput();
                System.out.println("Enter the phone number of the customer:");
                phoneNumber = prompter.nextInput();
                // Search if mail is duplicated...
                manager.createCustomer(firstName, name, mail, phoneNumber);
                break;
            case "2":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                manager.deleteCustomer(userId);
                break;
            case "3":
                importer.importCustomer();
                break;
            case "4":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                customersMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }

    /**
     * Displays the modify customers menu and processes user input.
     *
     * @param text whether to display the menu
     */
    private void modifyCustomers(boolean text) {
        if (text) {
            System.out.println("""
                    What do you want to modify?
                    |0|: Mail
                    |1|: Phone Number
                    |2|: Back to Customer Menu
                    """);
        }
        String answer = prompter.nextInput();
        int userId;
        switch (answer) {
            case "0":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the new email of the customer:");
                String newMail = prompter.nextInput();
                customer.modifyMail(userId, newMail);
                break;
            case "1":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the new phone number of the customer:");
                String newPhoneNumber = prompter.nextInput();
                customer.modifyPhoneNumber(userId, newPhoneNumber);
                break;
            case "2":
                customersMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                modifyCustomers(false);
        }
        System.out.println();
        initialMenu(true);
    }

    /**
     * Displays the report menu and processes user input.
     *
     * @param text whether to display the menu
     */
    private void reportMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Enter the desired output:
                    |0|: All books
                    |1|: All borrowed book copies
                    |2|: All non-borrowed book copies
                    |3|: All customers
                    |4|: All currently borrowed book copies of a customer
                    |5|: Number of book copies per publisher
                    |6|: Back to Initial Menu
                    """);
        }
        String answer = prompter.nextInput();
        int userId;
        switch (answer) {
            case "0":
                reports.allBooks();
                break;
            case "1":
                reports.allBorrowedCopies();
                break;
            case "2":
                reports.allNonBorrowedCopies();
                break;
            case "3":
                reports.allCustomers();
                break;
            case "4":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                reports.BorrowedCustomer(userId);
                break;
            case "5":
                reports.NumberOfBookCopiesPerPublisher();
                break;
            case "6":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                reportMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }
}