package prototype;

import prototype.commands.Books;
import prototype.commands.Exit;
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

                break;
            case "2":

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
                    "|4|: Import\n|5|: Back to Initial Menu\n");
        }
        String answer = prompter.nextInput();
        String bookId, userId;
        switch (answer) {
            case "0":
                searchMenu(true);
                break;
            case "1":
                bookId = prompter.nextInput();
                userId = prompter.nextInput();
                String borrowDays = prompter.nextInput();
                Books.borrow(bookId, userId,borrowDays);
                break;
            case "2":
                bookId = prompter.nextInput();
                userId = prompter.nextInput();
                Books.returnBook(bookId, userId);
                break;
            case "3":
                bookId = prompter.nextInput();
                Books.deleteBook(bookId);
                break;
            case "4":
                String title = prompter.nextInput();
                String author = prompter.nextInput();
                String year = prompter.nextInput();
                String genre = prompter.nextInput();
                Books.importBook(title, author, year, genre);
                break;
            case "5":
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
                String title = prompter.nextInput();
                Books.searchByTitle(title);
                break;
            case "1":
                String author = prompter.nextInput();
                Books.searchByAuthor(author);
                break;
            case "2":
                String year = prompter.nextInput();
                Books.searchByYear(year);

                break;
            case "3":
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
}
