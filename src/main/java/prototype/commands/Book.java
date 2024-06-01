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

    /**
     * This method creates some examples to be able to test
     */
    public static void creationBooks() {
        new Book("Berenjena", "0-7642-1858-1", "Dr Pepper", "1980");
        new Book("Tomatoes", "0-7050-3533-6", "Ibañez", "2005");
    }

    public static void deleteBook(String isbn) {
        ArrayList<Integer> copiesToDelete = new ArrayList<>();

        for (int copyId : BookCopy.getCopyToBookMap().keySet()) {
            if (Objects.equals(BookCopy.getCopyToBookMap().get(copyId), isbn) && BookCopy.getBorrowStatus().get(copyId)) {
                System.out.println("Cannot delete book because there are copies currently borrowed.");
                return;
            } else if (Objects.equals(BookCopy.getCopyToBookMap().get(copyId), isbn)) {
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
