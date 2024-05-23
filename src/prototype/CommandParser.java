package prototype;

import prototype.commands.*;
import prototype.prompt.Prompter;

public class  CommandParser {
    private final Prompter prompter = new Prompter();

    public void creation(){
        Customer.creationCustomers();
        Book.creationBooks();
        BookCopy.creationBookCopies();
    }

    public void initialMenu(boolean text)
    {
        if (text)
        {
            System.out.println("""
                    Initial Menu DEVELOPMENT:
                    |0|: Books
                    |1|: Books Copies
                    |2|: Customer
                    |3|: Reporting
                    |4|: Exit
                    """);
        }
        String answer = prompter.nextInput().strip();
        switch (answer)
        {
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

    private void booksMenu(boolean text) {
        if (text){
            System.out.println("""
                    |0|: Search
                    |1|: Import Books
                    |2|: Delete
                    |3|: Back to Initial Menu
                    """);
        }
        String answer = prompter.nextInput().strip();
        String title, isbn, author, year, genre;
        switch (answer) {
            case "0":
                searchMenu(true);
                break;
            case "1":
                System.out.println("Enter a title:");
                title = prompter.nextInput();
                System.out.println("Enter a ISBN:");
                isbn = prompter.nextInput();
                System.out.println("Enter the author:");
                author = prompter.nextInput();
                System.out.println("Enter a year:");
                year = prompter.nextInput();
                System.out.println("Enter a genre:");
                genre = prompter.nextInput();
                Book.importBook(title, isbn, author, year, genre);
                break;
            case "2":
                System.out.println("Enter the ISBN of the book:");
                isbn = prompter.nextInput();
                Book.deleteBook(isbn);
                break;
            case "3":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                booksMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }
    private void searchMenu(boolean text) {
        if (text) {
            System.out.println("""
                    Search by:
                    |0|: Title
                    |1|: Author
                    |2|: Year
                    |3|: Genre
                    |4|: Back to Books Menu
                    """);
        }
        String answer = prompter.nextInput();
        switch (answer) {
            case "0":
                System.out.println("Enter a title:");
                String title = prompter.nextInput();
                Book.searchByTitle(title);
                break;
            case "1":
                System.out.println("Enter an author:");
                String author = prompter.nextInput();
                Book.searchByAuthor(author);
                break;
            case "2":
                System.out.println("Enter a year:");
                String year = prompter.nextInput();
                Book.searchByYear(year);
                break;
            case "3":
                System.out.println("Enter a genre:");
                String genre = prompter.nextInput();
                Book.searchByGenre(genre);
                break;
            case "4":
                booksMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                searchMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }
    private void booksCopiesMenu() {
        System.out.println("""
                |0|: Borrow
                |1|: Return
                |2|: Import Books Copies
                |3|: Delete BookCopy
                |4|: Back to Initial Menu
                """);
        String answer = prompter.nextInput();
        int userId, copyId;
        String isbn;
        switch (answer) {
            case "0":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                System.out.println("For how many days do he wants to borrow it?");
                String borrowDays = prompter.nextInput();
                BookCopy.borrow(copyId, userId,borrowDays);
                break;
            case "1":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                BookCopy.returnBook(copyId, userId);
                break;
            case "2":
                System.out.println("Enter the ID of the book:");
                isbn = prompter.nextInput();
                //Not implemented yet
                //BookCopy.importBookCopy(isbn);
                break;
            case "3":
                System.out.println("Enter the ID of the book copy:");
                copyId = Integer.parseInt(prompter.nextInput());
                BookCopy.delete(copyId);
                break;
            case "4":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                booksMenu(false);
        }
        System.out.println();
        initialMenu(true);
    }

    private void customersMenu(boolean text) {
        if (text){
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
                System.out.println("Enter the email of the customer:");
                mail = prompter.nextInput();
                System.out.println("Enter the phone number of the customer:");
                phoneNumber = prompter.nextInput();
                // Search if mail is duplicated...
                new Customer(mail,phoneNumber);
                break;
            case "2":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                Customer.delete(userId);
                break;
            case "3":
                System.out.println("Enter the email of the customer:");
                mail = prompter.nextInput();
                System.out.println("Enter the phone number of the customer:");
                phoneNumber = prompter.nextInput();
                Customer.importCustomer(mail, phoneNumber);
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
                Customer.modifyMail(userId,newMail);
                break;
            case "1":
                System.out.println("Enter the ID of the customer:");
                userId = Integer.parseInt(prompter.nextInput());
                System.out.println("Enter the new phone number of the customer:");
                String newPhoneNumber = prompter.nextInput();
                Customer.modifyPhoneNumber(userId,newPhoneNumber);
                break;
            case "2":
                customersMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                modifyCustomers(false);
        }
        System.out.println();
        initialMenu(true);    }
    private void reportMenu(boolean text) {
        if (text){
            System.out.println("""
                    Enter the desired output: |0|: All books
                    |1|: All borrowed book copies
                    |2|: All non-borrowed book copies
                    |3|: All customers
                    |4|: All currently borrowed book copies of a customer
                    |5|: Back to Initial Menu
                    """);
        }
        String answer = prompter.nextInput();
        String userId;
        switch (answer) {
            case "0":
                Reports.allBooks();
                break;
            case "1":
                Reports.allBorrowed();
                break;
            case "2":
                Reports.allNonBorrowed();
                break;
            case "3":
                Reports.allCustomers();
                break;
            case "4":
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
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
