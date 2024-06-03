package prototype;

import prototype.commands.*;
import prototype.prompt.Prompter;

public class CommandParser {
    private static final Prompter prompter = new Prompter();

    public void creation() {
        Manager.creationCustomers();
        Manager.creationBooks();
        Manager.creationBookCopies();
    }

    public static void initialMenu(boolean text) {
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

    private static void booksMenu(boolean text) {
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
                Importer.importBook();
                break;
            case "1":
                System.out.println("Enter the ISBN of the book:");
                isbn = prompter.nextInput();
                Manager.deleteBook(isbn);
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

    private static void searchMenu(boolean text) {
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
                Manager.searchByTitle(title);
                break;
            case "1":
                System.out.println("Enter an author:");
                String author = prompter.nextInput();
                Manager.searchByAuthor(author);
                break;
            case "2":
                System.out.println("Enter a ISBN:");
                String isbn = prompter.nextInput();
                Manager.searchByISBN(isbn);
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

    private static void booksCopiesMenu() {
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
                Manager.borrowBookCopy(copyId, userId);
                break;
            case "1":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                Manager.returnBookCopy(copyId, userId);
                break;
            case "2":
                searchMenu(true);
                break;
            case "3":
                Importer.importBookCopy();
                break;
            case "4":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                Manager.deleteBookCopy(copyId);
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

    private static void customersMenu(boolean text) {
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
                Manager.createCustomer(firstName, name, mail, phoneNumber);
                break;
            case "2":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                Manager.deleteCustomer(userId);
                break;
            case "3":
                Importer.importCustomer();
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

    private static void modifyCustomers(boolean text) {
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
                Customer.modifyMail(userId, newMail);
                break;
            case "1":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the new phone number of the customer:");
                String newPhoneNumber = prompter.nextInput();
                Customer.modifyPhoneNumber(userId, newPhoneNumber);
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

    private static void reportMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Enter the desired output:
                    |0|: All books
                    |1|: All borrowed book copies
                    |2|: All non-borrowed book copies
                    |3|: All customers
                    |4|: All currently borrowed book copies of a customer
                    |5|: Back to Initial Menu
                    """);
        }
        String answer = prompter.nextInput();
        int userId;
        switch (answer) {
            case "0":
                Reports.allBooks();
                break;
            case "1":
                Reports.allBorrowedCopies();
                break;
            case "2":
                Reports.allNonBorrowedCopies();
                break;
            case "3":
                Reports.allCustomers();
                break;
            case "4":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                Reports.BorrowedCustomer(userId);
            case "5":
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


