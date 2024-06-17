package prototype.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * The `Book` class represents a book entity in the library management system.
 * It contains information such as title, author, ISBN, year, and book ID.
 */
public class Book {
    private String title, author, isbn, year;
    private static int nextBookId = 1;
    private static int bookId;
    private static ArrayList<Book> books = new ArrayList<>();

    /**
     * Constructs a new Book object with the given parameters.
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @param isbn   The ISBN of the book.
     * @param year   The publication year of the book.
     */
    public Book(String title, String author, String isbn, String year) {
        if (!isValidIsbn(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN: " + isbn);
        }
        if (!isValidYear(year)) {
            throw new IllegalArgumentException("Invalid year: " + year);
        }
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.year = year;
        bookId = nextBookId++;
        if (!sameBook(isbn)) {
            books.add(this);
        }
    }

    /**
     * Validates the ISBN format.
     *
     * @param isbn The ISBN to validate.
     * @return True if the ISBN is valid, false otherwise.
     */
    public static boolean isValidIsbn(String isbn) {
        return isbn.matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$");
    }

    /**
     * Validates the publication year.
     *
     * @param year The publication year to validate.
     * @return True if the year is valid, false otherwise.
     */
    public static boolean isValidYear(String year) {
        try {
            int yearValue = Integer.parseInt(year);
            return yearValue >= 868 && yearValue <= LocalDate.now().getYear();   // 868 - first ever published book according to Internet
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a book with the given ISBN already exists in the collection.
     *
     * @param isbn The ISBN to check.
     * @return True if a book with the given ISBN exists, false otherwise.
     */
    public static boolean sameBook(String isbn) {
        for (Book book : books) {
            if (Objects.equals(book.isbn, isbn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a book by its ISBN from the collection.
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return The Book object with the given ISBN, or null if not found.
     */
    public static Book getBookByIsbn(String isbn) {
        for (Book book : books) {
            if (Objects.equals(book.getIsbn(), isbn)) {
                return book;
            }
        }
        return null; // Return null if no book with the given ISBN is found
    }

    //GETTERS AND SETTERS
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static int getNextBookId() {
        return nextBookId;
    }

    public static void setNextBookId(int nextBookId) {
        Book.nextBookId = nextBookId;
    }

    public static ArrayList<Book> getBooks() {
        return books;
    }

    public static void setBooks(ArrayList<Book> books) {
        Book.books = books;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (!isValidIsbn(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN: " + isbn);
        }
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        if (!isValidYear(year)) {
            throw new IllegalArgumentException("Invalid year: " + year);
        }
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }
}