package prototype.commands;

import java.time.LocalDate;

public class Books {
    public static void searchByTitle(String title) {
        System.out.println("Searching...");
    }
    public static void searchByAuthor(String author) {
        System.out.println("Searching...");
    }
    public static void searchByYear(String year) {
        System.out.println("Searching...");
    }
    public static void searchByGenre(String genre) {
        System.out.println("Searching...");
    }

    public static void borrow(String bookId, String userId, String borrowDays) {
        // LocalDate returnDate = LocalDate.now().plusDays(Integer.parseInt(borrowDays));
        System.out.println("Borrowing...");
    }

    public static void returnBook(String bookId, String userId) {
        System.out.println("Returning...");
    }

    public static void deleteBook(String bookId) {
        System.out.println("Deleting...");
    }

    public static void importBook(String title, String author, String year, String genre) {
        System.out.println("Importing...");
    }
    public static void importBookCopy(String title, String author, String year, String genre) {
        System.out.println("Importing...");
    }
}
