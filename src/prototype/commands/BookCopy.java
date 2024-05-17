package prototype.commands;

import java.util.HashMap;

public class BookCopy {
    private static int nextBookId = 1;
    int copyId;
    static boolean borrowed;
    String isbn;
    static HashMap<Integer, String> relationsBooks = new HashMap<>();
    static HashMap<Integer, Boolean> borrowStatus = new HashMap<>();

    public BookCopy(String isbn) {
        this.isbn = isbn;
        this.copyId = nextBookId++;
        relationsBooks.put(copyId, isbn);
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
        if (relationsBooks.containsKey(copyId) && !borrowStatus.get(copyId)) {
            relationsBooks.remove(copyId);
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
        // LocalDate returnDate = LocalDate.now().plusDays(Integer.parseInt(borrowDays));
        if (relationsBooks.containsKey(copyId) && !borrowStatus.get(copyId)) {
            borrowStatus.put(copyId, true);
            System.out.println("Book copy borrowed successfully." + relationsBooks.get(copyId));
        } else {
            if (borrowStatus.get(copyId)){
                System.out.println("book copy is already borrowed");
            } else {
                System.out.println("No book copies with that ID");
            }
        }
    }
    public static void returnBook(int copyId, int userId) {
        if (relationsBooks.containsKey(copyId) && borrowStatus.get(copyId)) {
            borrowStatus.put(copyId, false);
            System.out.println("Book copy returned successfully.");
        } else {
            System.out.println("Book copy is not borrowed or does not exist.");
        }
    }

    public static void overdueFee(int userId) {
        // If Localdate.now is after the returnDate then it applies a fee.
        // Read the returnDate in the file and see if the Customer need to pay a fee.
        System.out.println("Overdue...");
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public static void setBorrowed(boolean borrowed) {
        BookCopy.borrowed = borrowed;
    }
}
