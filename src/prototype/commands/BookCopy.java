package prototype.commands;

import java.time.LocalDate;
import java.util.HashMap;

public class BookCopy {
    private static int nextBookId = 1;
    private final int copyId;
    private static boolean borrowed;
    private final String isbn;
    /**
     * copyToBookMap maps every copyId with a ISBN.
     */
    static HashMap<Integer, String> copyToBookMap = new HashMap<>();
    /**
     * borrowStatus maps every copyId with a boolean, to know if the book was borrowed or not.
     */
    static HashMap<Integer, Boolean> borrowStatus = new HashMap<>();
    /**
     * copyBorrowers maps every copyId with a userId. Useful after to know quickly if that person borrowed the book.
     */
    static HashMap<Integer, Integer> copyBorrowers = new HashMap<>();
    // static HashMap<Integer, LocalDate> borrowDates = new HashMap<>(); // In the future we can implement something for delayed returned etc.

    public BookCopy(String isbn) {
        this.isbn = isbn;
        this.copyId = nextBookId++;
        copyToBookMap.put(copyId, isbn);
        borrowed = false;
        borrowStatus.put(copyId, false);
    }



    /**
     * @param isbn The ISBN of the book
     * @return the number of existing copies of a book
     */
    public static int quantityCopiesOfABook(String isbn) {
        int count = 0;
        for (String value : copyToBookMap.values()) {
            if (value.equals(isbn)) {
                count++;
            }
        }
        return count;
    }

    /**
     * This method creates some examples to be able to test.
     */
    public static void creationBookCopies() {
        new BookCopy("0-7642-1858-1");
        new BookCopy("0-7050-3533-6");
        new BookCopy("0-5472-1458-7");
    }

    /**
     * Method to import a book copy, creating a new object.
     * Not implemented yet
     * @param isbn You need the isbn to know which books object refers to.
     */
    /*public static void importBookCopy(String isbn) {
        new BookCopy(isbn);
        System.out.println("Book copy imported successfully.");
    }
     */

    /**
     * This method deletes a book copy, making sure it exists and is not borrowed.
     *
     * @param copyId
     */
    public static boolean delete(int copyId) {
        if (copyToBookMap.containsKey(copyId) && !borrowStatus.get(copyId)) {
            copyToBookMap.remove(copyId);
            borrowStatus.remove(copyId);
            System.out.println("Book copy (id = " + copyId + ") was deleted successfully");
            return true;
        } else {
            if (!copyToBookMap.containsKey(copyId)) {
                System.out.println("No book copies with that ID");
            } else {
                System.out.println("Book copy is currently borrowed.");
            }
        }
        return false;
    }

    /**
     * This method allows a valid userId to borrow a book copy. In the borrowStatus Hashmap you specify its true
     * and in the copyBorrowers you specify who did borrow that book with the userId.
     *
     * @param copyId     The id of the copy to be borrowed
     * @param userId     The id of the user who wants the book copy
     * @param borrowDays It will be to specify how many days you want to borrow the book (Not implemented yet)
     */
    public static void borrow(int copyId, int userId, String borrowDays) {
        // here we should add a conditional for limiting the amount of books a userId can borrow
        if (copyToBookMap.containsKey(copyId) && !borrowStatus.get(copyId) && Customer.customerExists(userId)) {
            borrowStatus.put(copyId, true);
            copyBorrowers.put(copyId, userId);
            System.out.println("Book copy borrowed successfully.");
        } else {
            if (!copyToBookMap.containsKey(copyId)) {
                System.out.println("There doesnt exist any book copies with that ID");
            } else if (borrowStatus.get(copyId)) {
                System.out.println("Book copy is already borrowed.");
            } else if (!Customer.customerExists(userId)) {
                System.out.println("The given user does not exist.");
            }
        }
    }

    /**
     * This method allows a userId to return a copyId if previous he borrowed it. You check if the copyId is valid, then
     * if the borrowStatus is true (means borrowed) and the copyBorrowers matches, so we know the person is the same.
     *
     * @param copyId The id of the book to be returned
     * @param userId The id of the user who wants to return the book copy.
     */
    public static void returnBook(int copyId, int userId) {
        if (copyToBookMap.containsKey(copyId) && borrowStatus.get(copyId) && copyBorrowers.get(copyId) == userId) {
            System.out.println("Book copy returned successfully.");
            borrowStatus.put(copyId, false);
            copyBorrowers.remove(copyId); // Remove borrower record
        } else {
            if (!copyToBookMap.containsKey(copyId)) {
                System.out.println("Book copy does not exist.");
            } else if (!borrowStatus.get(copyId)) {
                System.out.println("The book is not borrowed.");
            } else if (!(copyBorrowers.get(copyId) == userId)) {
                System.out.println("The book is borrowed by another user.");
            }
        }
    }

    // Not implemented yet.
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

    public static int getNextBookId() {
        return nextBookId;
    }
    public static HashMap<Integer, String> getCopyToBookMap() {
        return copyToBookMap;
    }

    public static HashMap<Integer, Boolean> getBorrowStatus() {
        return borrowStatus;
    }

    public static int getBorrowerId(int copyId) {
        return copyBorrowers.getOrDefault(copyId, -1);
    }
}