package prototype.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookCopy {
    private static int nextBookId = 1;
    private final int copyId;
    private boolean borrowed;
    private LocalDate borrowedDate;
    private final String isbn;
    private String shelfLocation;
    private int userId;
    static ArrayList<BookCopy> bookCopies = new ArrayList<>();


    public BookCopy(String isbn, String shelfLocation) {
        this.isbn = isbn;
        this.shelfLocation = shelfLocation;
        this.copyId = nextBookId++;
        borrowed = false;
        userId = -1;
        borrowedDate = LocalDate.now();
        bookCopies.add(this);
    }

    public static int getNextBookId() {
        return nextBookId;
    }

    public static void setNextBookId(int nextBookId) {
        BookCopy.nextBookId = nextBookId;
    }

    public int getCopyId() {
        return copyId;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public static void setShelfLocation(String shelfLocation) {
        shelfLocation = shelfLocation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static ArrayList<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public static void setBookCopies(ArrayList<BookCopy> bookCopies) {
        BookCopy.bookCopies = bookCopies;
    }
}