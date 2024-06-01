package prototype.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Book {
    private String title, author, isbn, year;
    private static int nextBookId = 1;
    private final int bookId;
    private static ArrayList<Book> books = new ArrayList<>();

    public Book(String title, String isbn, String author, String year) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.year = year;
        bookId = nextBookId++;
        if (!sameBook(isbn)) {
            books.add(this);
        }
    }

    public static boolean sameBook(String isbn) {
        for (Book book : books) {
            if (Objects.equals(book.isbn, isbn)) {
                return true;
            }
        }
        return false;
    }

    public static Book getBookByIsbn(String isbn) {
        for (Book book : books) {
            if (Objects.equals(book.getIsbn(), isbn)) {
                return book;
            }
        }
        return null; // Return null if no book with the given ISBN is found
    }

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
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }
}
