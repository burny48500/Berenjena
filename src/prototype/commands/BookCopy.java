package prototype.commands;

import java.time.LocalDate;
import java.util.HashMap;

public class BookCopy {
    private static int nextBookId = 1;
    int copyId;
    static boolean borrowed;
    String isbn;
    static HashMap<Integer, String> copyToBookMap = new HashMap<>();
    static HashMap<Integer, Boolean> borrowStatus = new HashMap<>();
    static HashMap<Integer, Integer> copyBorrowers = new HashMap<>();
    // static HashMap<Integer, LocalDate> borrowDates = new HashMap<>(); // In the future we can implement something for delayed returned etc.

    public BookCopy(String isbn) {
        this.isbn = isbn;
        this.copyId = nextBookId++;
        copyToBookMap.put(copyId, isbn);
        borrowed = false;
        borrowStatus.put(copyId, false);
    }

    public static void creationBookCopies(){
        BookCopy berenjena = new BookCopy("0-7642-1858-1");
        BookCopy tomatoes = new BookCopy("0-7050-3533-6");
        BookCopy tomatoes2 = new BookCopy("0-7642-1858-2");
    }

    public static void importBookCopy(String isbn) {
        new BookCopy(isbn);
        System.out.println("Book copy imported successfully.");
    }

    public static void delete(int copyId){
        if (copyToBookMap.containsKey(copyId) && !borrowStatus.get(copyId)) {
            copyToBookMap.remove(copyId);
            borrowStatus.remove(copyId);
            System.out.println("Book copy deleted successfully.");
        } else {
            if (borrowStatus.get(copyId)){
                System.out.println("Book copy is currently borrowed.");
            } else {
                System.out.println("No book copies with that ID");
            }
        }
    }

    public static void borrow(int copyId, int userId, String borrowDays) {
        // here we should add a conditional for limiting the amount of books a userId can borrow
        if (copyToBookMap.containsKey(copyId) && !borrowStatus.get(copyId)) {
            borrowStatus.put(copyId, true);
            copyBorrowers.put(copyId, userId);
            System.out.println("Book copy borrowed successfully.");
        } else {
            if (borrowStatus.get(copyId)) {
                System.out.println("Book copy is already borrowed.");
            } else {
                System.out.println("No book copies with that ID.");
            }
        }
    }

    public static void returnBook(int copyId, int userId) {
        if (copyToBookMap.containsKey(copyId) && borrowStatus.get(copyId) && copyBorrowers.get(copyId) == userId) {
            System.out.println("Book copy returned successfully.");
            borrowStatus.put(copyId, false);
            copyBorrowers.remove(copyId); // Remove borrower record
        } else {
            if (!copyToBookMap.containsKey(copyId)){
                System.out.println("Book copy does not exist.");
            } else if (!borrowStatus.get(copyId)) {
                System.out.println("The book is not borrowed.");
            } else if (!(copyBorrowers.get(copyId) == userId)) {
                System.out.println("The book is borrowed by another user.");
            }
        }
    }

    public static double calculateOverdueFee(long overdueDays) {
        return overdueDays * 1.0;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public static void setBorrowed(int copyId, boolean borrowed) {
        if (borrowStatus.containsKey(copyId)) {
            borrowStatus.put(copyId, borrowed);
        }
    }

    public int getCopyId() {
        return copyId;
    }

    public static int getBorrowerId(int copyId) {
        return copyBorrowers.getOrDefault(copyId, -1);
    }
}
