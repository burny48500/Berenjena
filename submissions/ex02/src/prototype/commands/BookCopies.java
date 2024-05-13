package prototype.commands;

import java.time.LocalDate;

public class BookCopies {
    String title,author,year,genre;
    String userId, bookId;
    public static void importBookCopy(String title, String author, String year, String genre) {
        System.out.println("Importing...");
    }
    public static void borrow(String bookId, String userId, String borrowDays) {
        // LocalDate returnDate = LocalDate.now().plusDays(Integer.parseInt(borrowDays));
        // Write the returnDate in a file to store it
        System.out.println("Borrowing...");
    }
    public static void overdueFee(String userId) {
        // If Localdate.now is after the returnDate then it applies a fee.
        // Read the returnDate in the file and see if the Customer need to pay a fee.
        System.out.println("Overdue...");
    }

    public static void returnBook(String bookId, String userId) {
        System.out.println("Returning...");
        // If time is exceeded, then we call the overdueFee method with userId parameter and the date it should be returned.
        overdueFee(userId);
    }
}
