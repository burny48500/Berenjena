package prototype.commands;

public class BookCopies {
    public static void importBookCopy(String title, String author, String year, String genre) {
        System.out.println("Importing...");
    }
    public static void borrow(String bookId, String userId, String borrowDays) {
        // LocalDate returnDate = LocalDate.now().plusDays(Integer.parseInt(borrowDays));
        System.out.println("Borrowing...");
    }

    public static void returnBook(String bookId, String userId) {
        System.out.println("Returning...");
    }
}
