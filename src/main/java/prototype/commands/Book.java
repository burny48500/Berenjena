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
        if (!sameBook(isbn)) {
            books.add(this);
        }
    }

    /**
     * This method creates some examples to be able to test
     */
    public static void creationBooks() {
        new Book("Berenjena", "0-7642-1858-1", "Group", "2024", "University");
        new Book("Tomatoes", "0-7050-3533-6", "Group", "2024", "University");
    }

    public static void searchByTitle(String title) {
        System.out.println("Searching...");
    }

    public static void searchByAuthor(String author) {
        System.out.println("Searching...");
    }

    public static void searchByISBN(String year) {
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

    /*
    public static void importBook(String title, String isbn, String author, String year, String genre) {
        Book bookToBeImported= new Book(title, isbn, author, year, genre);
        if (!books.contains(bookToBeImported)) {
            books.add(bookToBeImported);
            System.out.println("Book imported successfully.");
        }else {
            System.out.println("Book already imported.");
        }
    }
    */

    public static boolean sameBook(String isbn) {
        for (Book book : books) {
            if (Objects.equals(book.isbn, isbn)) {
                return true;
            }
        }
        return false;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public static int getBookId() {
        return bookId;
    }

    public static void setBookId(int bookId) {
        Book.bookId = bookId;
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

}