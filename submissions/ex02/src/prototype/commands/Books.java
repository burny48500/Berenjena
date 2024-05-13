package prototype.commands;

import java.time.LocalDate;

public class Books {
    String title,author,year,genre;
    String userId, bookId;
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

    public static void deleteBook(String bookId) {
        System.out.println("Deleting...");
    }
    public static void importBook(String title, String author, String year, String genre) {
        System.out.println("Importing...");
    }
}
