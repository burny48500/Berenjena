package prototype.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * borrowDates maps every copyId with the date in which the book was borrowed.
     */
    static HashMap<Integer, String> borrowDates = new HashMap<>();

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
     */
    public static void borrow(int copyId, int userId) {
        if (amountOfBooksPerCustomer(copyBorrowers, userId) <= 5){
            if (copyToBookMap.containsKey(copyId) && !borrowStatus.get(copyId) && Customer.customerExists(userId)) {
                borrowStatus.put(copyId, true);
                // DATES
                LocalDate currentDate = LocalDate.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                String formattedDate = currentDate.format(formatter);
                borrowDates.put(copyId, formattedDate);
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
        }else {
            System.out.println("This customer can not borrow more book copies.");
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
            copyBorrowers.remove(copyId);
            borrowDates.remove(copyId);
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

    public static int amountOfBooksPerCustomer(HashMap<Integer, Integer> copyBorrowers, int userId) {
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : copyBorrowers.entrySet()) {
            if (entry.getValue().equals(userId)) {
                count++;
            }
        }
        return count;
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