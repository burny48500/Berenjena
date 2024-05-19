package prototype.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Book {
    private String title, isbn, author, year, genre;
    private static int nextBookId = 1;
    private static int bookId;
    private static ArrayList<Book> books = new ArrayList<>();
    public Book(String title, String isbn, String author, String year, String genre) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.bookId = nextBookId++;
        books.add(this);
    }

    /**
     * This method creates some examples to be able to test
     */
    public static void creationBooks(){
        new Book("Berenjena", "0-7642-1858-1", "Group", "2024", "University");
        new Book("Tomatoes", "0-7050-3533-6", "Group", "2024", "University");
    }

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

    public static void deleteBook(String isbn) {
        ArrayList<Integer> copiesToDelete = new ArrayList<>();

        for (int copyId : BookCopy.copyToBookMap.keySet()) {
            if (Objects.equals(BookCopy.copyToBookMap.get(copyId), isbn) && BookCopy.borrowStatus.get(copyId)) {
                System.out.println("Cannot delete book because there are copies currently borrowed.");
                return;
            } else if (Objects.equals(BookCopy.copyToBookMap.get(copyId), isbn)) {
                copiesToDelete.add(copyId);
            }
        }

        for (int copyId : copiesToDelete) {
            BookCopy.delete(copyId);
        }

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (Objects.equals(book.isbn, isbn)) {
                iterator.remove();
                System.out.println("Book removed successfully.");
                return;
            }
        }
        System.out.println("No books with that ISBN were found.");
    }
    public static void importBook(String title, String isbn, String author, String year, String genre) {
        books.add(new Book(title, isbn, author, year, genre));
        System.out.println("Book imported successfully.");
    }
}
