package prototype;

import prototype.commands.Accounts;
import prototype.commands.Books;
import prototype.commands.Exit;
import prototype.commands.Reports;
import prototype.prompt.Prompter;

import java.time.LocalDate;
import java.util.Date;

public class CommandParser {
    private final Prompter prompter = new Prompter();
    public void initialMenu(boolean text)
    {
        if (text)
        {
            System.out.println("Intial Menu:\n|0|: Books\n|1|: Accounts\n|2|: Reporting\n|3|: Exit\n");
        }
        String answer = prompter.nextInput().strip();
        switch (answer)
        {
            case "0":
                booksMenu(true);
                break;
            case "1":
                accountsMenu(true);
                break;
            case "2":
                reportMenu(true);
                break;
            case "3":
                prompter.close();
                Exit.close();
            default:
                System.out.println("Wrong input. Try again.");
                initialMenu(false);
        }
    }

    private void booksMenu(boolean text) {
        if (text){
            System.out.println("|0|: Search\n|1|: Borrow\n|2|: Return\n|3|: Delete\n" +
                    "|4|: Import Books \n|5|: Import Books Copies\n|6|: Back to Initial Menu\n");
        }
        String answer = prompter.nextInput();
        String bookId, userId, title, author, year, genre;
        switch (answer) {
            case "0":
                searchMenu(true);
                break;
            case "1":
                System.out.println("Enter the ID of the book:");
                bookId = prompter.nextInput();
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
                String borrowDays = prompter.nextInput();
                Books.borrow(bookId, userId,borrowDays);
                break;
            case "2":
                System.out.println("Enter the ID of the book:");
                bookId = prompter.nextInput();
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
                Books.returnBook(bookId, userId);
                break;
            case "3":
                bookId = prompter.nextInput();
                Books.deleteBook(bookId);
                break;
            case "4":
                System.out.println("Enter a title:");
                title = prompter.nextInput();
                System.out.println("Enter the author:");
                author = prompter.nextInput();
                System.out.println("Enter a year:");
                year = prompter.nextInput();
                System.out.println("Enter a genre:");
                genre = prompter.nextInput();
                Books.importBook(title, author, year, genre);
                break;
            case "5":
                System.out.println("Enter a title:");
                title = prompter.nextInput();
                System.out.println("Enter an author:");
                author = prompter.nextInput();
                System.out.println("Enter a year:");
                year = prompter.nextInput();
                System.out.println("Enter a genre:");
                genre = prompter.nextInput();
                Books.importBookCopy(title, author, year, genre);
            case "6":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                booksMenu(false);
        }
    }
    private void searchMenu(boolean text) {
        if (text) {
            System.out.println("Search by: \n|0|: Title\n|1|: Author\n|2|: Year\n|3|: Genre\n" +
                    "|4|: Back to Books Menu\n");
        }
        String answer = prompter.nextInput();
        switch (answer) {
            case "0":
                System.out.println("Enter a title:");
                String title = prompter.nextInput();
                Books.searchByTitle(title);
                break;
            case "1":
                System.out.println("Enter an author:");
                String author = prompter.nextInput();
                Books.searchByAuthor(author);
                break;
            case "2":
                System.out.println("Enter a year:");
                String year = prompter.nextInput();
                Books.searchByYear(year);
                break;
            case "3":
                System.out.println("Enter a genre:");
                String genre = prompter.nextInput();
                Books.searchByGenre(genre);
                break;
            case "4":
                booksMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                searchMenu(false);
        }
    }

    private void accountsMenu(boolean text) {
        if (text){
            System.out.println("|0|: Modify Mail\n|1|: Create\n|2|: Delete\n|3|: Import Customer file\n|4|:Back to Accounts Menu\n");
        }
        String answer = prompter.nextInput();
        String userId, name, mail, phoneNumber;
        switch (answer) {
            case "0":
                modifyAccounts(true);
                break;
            case "1":
                System.out.println("Enter the name of the customer:");
                name = prompter.nextInput();
                System.out.println("Enter the email of the customer:");
                mail = prompter.nextInput();
                System.out.println("Enter the phone number of the customer:");
                phoneNumber = prompter.nextInput();
                // Search if mail is duplicated...
                Accounts.create(name, mail, phoneNumber);
                break;
            case "2":
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
                Accounts.delete(userId);
                break;
            case "3":
                System.out.println("Enter the name of the customer:");
                name = prompter.nextInput();
                System.out.println("Enter the email of the customer:");
                mail = prompter.nextInput();
                phoneNumber = prompter.nextInput();
                Accounts.importAccount(name, mail, phoneNumber);
            case "4":
                initialMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                accountsMenu(false);
        }
    }
    private void modifyAccounts(boolean text) {
        if (text) {
            System.out.println("What do you want to modify? \n|0|: Mail\n|1|: Phone Number\n|2|: Back to Accounts Menu\n");
        }
        String answer = prompter.nextInput();
        String userId;
        switch (answer) {
            case "0":
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
                System.out.println("Enter the new email of the customer:");
                String newMail = prompter.nextInput();
                Accounts.modifyMail(userId,newMail);
                break;
            case "1":
                System.out.println("Enter the ID of the customer:");
                userId = prompter.nextInput();
                System.out.println("Enter the new phone number of the customer:");
                String newPhoneNumber = prompter.nextInput();
                Accounts.modifyPhoneNumber(userId,newPhoneNumber);
                break;
            case "2":
                accountsMenu(true);
                break;
            default:
                System.out.println("Wrong input. Try again.");
                modifyAccounts(false);
        }
    }
    private void reportMenu(boolean text) {
        if (text){
            System.out.println("Enter the desired output: |0|: All books\n|1|: All borrowed book copies\n|2|: All non-borrowed book copies\n|3|: All customers\n" +
                    "|4|: All currently borrowed book copies of a customer\n|5|: Back to Initial Menu\n");
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
    }
}
