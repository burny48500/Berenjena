import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.commands.Manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the manager class.
 * Tests the functionality of managing books, book copies, and customers.
 */
public class ManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final Manager manager = new Manager();

    /**
     * Sets up the test environment by initializing necessary data and redirecting system output.
     */
    @BeforeEach
    public void setup() {
        manager.creationBooks();
        manager.creationBookCopies();
        manager.creationCustomers();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Tests deleting a book successfully.
     */
    @Test
    public void DeleteBookSuccessfullyTest() {
        Book bookToDelete = new Book("Frankenstein", "Mary Shelley", "0-2456-4821-5", "2007");
        Book book = new Book("Dune", "Frank Herbert", "0-3756-9921-4", "1972");
        assertTrue(Book.getBooks().contains(book));
        assertTrue(Book.getBooks().contains(bookToDelete));
        boolean deleted = manager.deleteBook("0-2456-4821-5");
        assertTrue(deleted);
        assertTrue(Book.getBooks().contains(book));
        assertFalse(Book.getBooks().contains(bookToDelete));
    }

    /**
     * Tests deleting a book that does not exist.
     */
    @Test
    public void DeleteNotFoundBookTest() {
        Book book1 = new Book("The Lord of the Rings", "J. R. R. Tolkien", "0-4956-9661-9", "1976");
        Book book2 = new Book("The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "0-1250-9821-7", "2002");

        String nonExistentISBN = "0-0000-0000-0";
        boolean foundCount = false;
        for (Book book : Book.getBooks()) {
            if (book.getIsbn().equals(nonExistentISBN)) {
                foundCount = true;
                break;
            }
        }
        assertFalse(foundCount, "Found non-existent ISBN during deletion test"); //extra check:)
        int bookSizeBeforeDeleting = Book.getBooks().size();
        manager.deleteBook(nonExistentISBN);
        assertEquals(bookSizeBeforeDeleting, Book.getBooks().size());
    }

    /**
     * Tests deleting a book with borrowed book copies.
     */
    @Test
    public void DeleteBookWithBorrowedBookCopiesTest() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "0-4545-3215-8", "1990");
        Customer customer = new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        BookCopy bookCopy = new BookCopy(book.getIsbn(), "B3", "Anaya");

        Book.getBooks().add(book);
        manager.borrowBookCopy(bookCopy.getCopyId(), customer.getUserId());
        manager.deleteBook(book.getIsbn());
        assertTrue(Book.getBooks().contains(book));
    }

    //book COPY
    //BORROW TESTS

    /**
     * Tests borrowing a valid book copy successfully.
     */
    @Test
    void borrowValidBookCopySuccessfullyTest() {
        int copyId = 1;
        int userId = 1;
        manager.borrowBookCopy(copyId, userId);
        BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == copyId)
                .findFirst()
                .orElse(null);

        assertNotNull(borrowedCopy);
        assertTrue(borrowedCopy.isBorrowed());
        assertEquals(userId, borrowedCopy.getUserId());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        assertEquals(formattedDate, borrowedCopy.getBorrowedDate().format(formatter));
    }

    /**
     * Tests borrowing a book copy that does not exist.
     */
    @Test
    void borrowNonExistentBookCopyTest() {
        int nonExistentId = 999999999;
        int userId = 1;
        manager.borrowBookCopy(nonExistentId, userId);
        BookCopy nonExistentCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == nonExistentId)
                .findFirst()
                .orElse(null);

        assertNull(nonExistentCopy);
    }

    /**
     * Tests borrowing a book copy that is already borrowed.
     */
    @Test
    void borrowAlreadyBorrowedBookCopyTest() {
        int copyId = 2;
        int userId = 1;
        manager.borrowBookCopy(copyId, userId);
        BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == copyId)
                .findFirst()
                .orElse(null);

        assertNotNull(borrowedCopy);
        assertTrue(borrowedCopy.isBorrowed());
        assertEquals(userId, borrowedCopy.getUserId());

        int newUserId = 2;
        manager.borrowBookCopy(copyId, newUserId);
        assertNotEquals(newUserId, borrowedCopy.getUserId());
        assertTrue(borrowedCopy.isBorrowed());
    }

    /**
     * Tests borrowing a book copy for a non-existent customer.
     */
    @Test
    void borrowBookCopyByNonExistentUserTest() {
        int copyId = 3;
        int nonExistentUserId = 999999999;
        manager.borrowBookCopy(copyId, nonExistentUserId);
        BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == copyId)
                .findFirst()
                .orElse(null);

        assertNotNull(borrowedCopy);
        assertFalse(borrowedCopy.isBorrowed());
        assertEquals(-1, borrowedCopy.getUserId());
    }

    /**
     * Tests borrowing a book copy for a customer with exceeded borrowing time.
     */
    @Test
    void borrowBookCopyExceedingLimitTest() {
        new BookCopy("978-0201485677", "A2", "Anaya");
        new BookCopy("978-0201485677", "B3", "Anaya");
        new BookCopy("978-0201485677", "C9", "Anaya");
        new BookCopy("0-7050-3533-99", "D2", "Anaya");
        new BookCopy("0-7050-3533-6", "E1", "Anaya");
        new BookCopy("0-7050-3533-6", "C4", "Anaya");

        int userId = 1;
        for (int copyId = 1; copyId <= 5; copyId++) {
            final int finalCopyId = copyId;
            manager.borrowBookCopy(finalCopyId, userId);
            BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                    .filter(copy -> copy.getCopyId() == finalCopyId)
                    .findFirst()
                    .orElse(null);

            assertNotNull(borrowedCopy);
            assertTrue(borrowedCopy.isBorrowed());
            assertEquals(userId, borrowedCopy.getUserId());
        }

        final int finalNewCopyId = 6;
        manager.borrowBookCopy(finalNewCopyId, userId);
        BookCopy newBorrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == finalNewCopyId)
                .findFirst()
                .orElse(null);

        assertNotNull(newBorrowedCopy);
        assertFalse(newBorrowedCopy.isBorrowed());
        assertEquals(-1, newBorrowedCopy.getUserId());
    }

    //DELETE TESTS

    /**
     * Tests deleting a book copy successfully.
     * Verifies that the specified book copy is removed from the system and is no longer present in the list of book copies.
     */
    @Test
    void deleteBookCopySuccessfullyTest() {
        BookCopy bookCopy = new BookCopy("0-7642-1858-1", "A2", "Anaya");
        int copyId = bookCopy.getCopyId();

        assertTrue(BookCopy.getBookCopies().contains(bookCopy));
        assertFalse(bookCopy.isBorrowed());

        assertTrue(manager.deleteBookCopy(copyId));
        assertFalse(BookCopy.getBookCopies().contains(bookCopy));
    }

    /**
     * Tests deleting a non-existent book copy.
     * Verifies that attempting to delete a book copy with a non-existent ID does not affect the system state.
     */
    @Test
    void deleteNotFoundBookCopyTest() {
        int nonExistentId = 999999;

        boolean foundId = false;
        for (BookCopy copy : BookCopy.getBookCopies()) {
            if (copy.getCopyId() == nonExistentId) {
                foundId = true;
                break;
            }
        }
        assertFalse(foundId, "Found non-existent copyID during deletion test"); //extra check :)
        assertFalse(manager.deleteBookCopy(nonExistentId));
    }

    /**
     * Tests deleting a borrowed book copy.
     * Verifies that a borrowed book copy cannot be deleted and remains in the system.
     */
    @Test
    void deleteBorrowedBookCopyTest() {
        BookCopy bookCopy = new BookCopy("0-5678-8901-2", "C2", "Anaya");
        int copyId = bookCopy.getCopyId();
        bookCopy.setBorrowed(true);
        assertTrue(bookCopy.isBorrowed());

        assertFalse(manager.deleteBookCopy(copyId));
        assertTrue(BookCopy.getBookCopies().contains(bookCopy));
        assertTrue(bookCopy.isBorrowed());
    }

    //RETURN book COPY

    /**
     * Tests the successful return of a book copy by a customer.
     * Verifies that the book copy is returned successfully, and the associated user ID is reset.
     */
    @Test
    void returnBookCopySuccessfullyTest() {
        int userId = 1;
        int copyId = 1;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(userId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(10));

        manager.returnBookCopy(copyId, userId);
        assertFalse(bookCopy.isBorrowed());
        assertEquals(-1, bookCopy.getUserId());
        assertEquals("book copy (id = " + copyId + ") was returned successfully", outContent.toString().trim());
        assertTrue(Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == userId && c.getPaymentStatus() == 0));
    }

    /**
     * Tests the attempt to return a book copy that is not borrowed by the specified customer.
     * Verifies that the return operation fails and the book copy remains borrowed by another user.
     */
    @Test
    void returnBookCopyNotBorrowedByCustomerTest() {
        int userId = 1;
        int copyId = 1;
        int wrongUserId = 2;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(wrongUserId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(7));

        manager.returnBookCopy(copyId, userId);
        assertEquals("The book copy (id = " + copyId + ") is not borrowed by that customer.", outContent.toString().trim());
        assertTrue(bookCopy.isBorrowed());
        assertEquals(wrongUserId, bookCopy.getUserId());
    }

    /**
     * Tests the attempt to return a non-existent book copy.
     * Verifies that the return operation fails and an appropriate message is displayed.
     */
    @Test
    void returnNonExistingBookCopyTest() {
        int userId = 1;
        int nonExistentCopyId = 999999;
        boolean found = BookCopy.getBookCopies().stream().anyMatch(bc -> bc.getCopyId() == nonExistentCopyId);
        assertFalse(found);
        manager.returnBookCopy(nonExistentCopyId, userId);
        assertEquals("The book copy (id = " + nonExistentCopyId + ") does not exist.", outContent.toString().trim());
    }

    /**
     * Tests the attempt to return a book copy with a non-existent customer ID.
     * Verifies that the return operation fails and an appropriate message is displayed.
     */
    @Test
    void returnBookCopyWithNonExistingCustomerTest() {
        int nonExistentUserId = 999999;
        int copyId = 1;
        boolean found = Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == nonExistentUserId);
        assertFalse(found);
        manager.returnBookCopy(copyId, nonExistentUserId);
        assertEquals("No customer with (id = " + nonExistentUserId + ") exists.", outContent.toString().trim());
    }

    /**
     * Tests the attempt to return a book copy that exceeds the maximum borrowing time, with the customer having unpaid fees.
     * Verifies that the return operation fails and the payment status of the customer is updated to indicate unpaid fees.
     */
    @Test
    void returnBookCopyExceedingBorrowingTime_CaseUnpaid_Test() {
        int userId = 1;
        int copyId = 1;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(userId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(40));

        manager.returnBookCopy(copyId, userId);
        String output = outContent.toString().trim();
        assertFalse(bookCopy.isBorrowed());
        assertEquals(-1, bookCopy.getUserId());
        assertTrue(output.contains("The maximum borrowing time is exceeded"));
        assertTrue(output.contains("The customer hasn't paid yet, the return is only possible after the payment of the fee"));
        assertTrue(Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == userId && c.getPaymentStatus() == 2));
    }

    /**
     * Tests the attempt to return a book copy that exceeds the maximum borrowing time, with the customer willing to pay the fees.
     * Verifies that the return operation succeeds after the customer pays the fees, and the payment status is updated accordingly.
     */
    @Test
    void returnBookCopyExceedingBorrowingTime_CasePaying_Test() {
        int userId = 1;
        int copyId = 1;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(userId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(40));
        String simulatedInput = "yes\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        manager.returnBookCopy(copyId, userId);
        String output = outContent.toString().trim();
        assertFalse(bookCopy.isBorrowed());
        assertEquals(-1, bookCopy.getUserId());
        assertTrue(output.contains("The maximum borrowing time is exceeded."));
        assertTrue(output.contains("The payment has been successful. book copy returned successfully"));
        assertTrue(Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == userId && c.getPaymentStatus() == 1));
    }

    //customer

    /**
     * Tests the scenario where a customer with borrowed books is deleted.
     * Verifies that the customer is still considered to exist after deletion.
     */
    @Test
    void deletingCustomerWithBorrowedBooksTest() {
        Customer customer = new Customer("Cid", "Miguel", "miguel@tum.de", "640882288");
        BookCopy bookCopy = new BookCopy("0-5678-8901-2", "C2", "Anaya");
        manager.borrowBookCopy(1, 1);
        int customerId = 1;
        manager.deleteCustomer(customerId);
        assertTrue(manager.customerExistsTests(customerId));
    }

    /**
     * Tests the scenario where a customer with no borrowed books is deleted.
     * Verifies that the customer is considered deleted after deletion.
     */
    @Test
    void deletingCustomerWithNoBorrowedBooksTest() {
        int customerId = 2;
        manager.deleteCustomer(customerId);
        assertFalse(manager.customerExistsTests(customerId), "not deleted");
    }

    /**
     * Tests the scenario where a non-existing customer is attempted to be deleted.
     * Verifies that the customer does not exist after attempted deletion.
     */
    @Test
    void deletingNonExistingCustomerTest() {
        int userId = Customer.getNextId() + 1;
        manager.deleteCustomer(userId);
        assertFalse(manager.customerExistsTests(userId));
    }

    // SEARCH TESTS

    /**
     * Tests the search functionality by title.
     * Verifies that the output contains the specified title.
     */
    @Test
    void searchByTitleTest() {
        String title = "Tomatoes";
        manager.searchByTitle(title);
        String output = outContent.toString().trim();
        assertTrue(output.contains("Tomatoes"));
    }

    /**
     * Tests the search functionality by author.
     * Verifies that the output contains the specified author.
     */
    @Test
    void searchByAuthorTest() {
        String author = "Dr Pepper";
        manager.searchByAuthor(author);
        String output = outContent.toString().trim();
        assertFalse(output.isEmpty());
        assertTrue(output.contains(author));
    }

    /**
     * Tests the search functionality by ISBN.
     * Verifies that the output contains the specified ISBN.
     */
    @Test
    void searchByISBNTest() {
        String isbn = "0-7642-1858-1";
        manager.searchByISBN(isbn);
        String output = outContent.toString().trim();
        assertFalse(output.isEmpty());
        assertTrue(output.contains(isbn));
    }

    /**
     * Tests the search functionality with a non-existent title.
     * Verifies that the output is empty.
     */
    @Test
    void searchByNonExistentTitleTest() {
        String title = "NonExistentTitle";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getTitle().equals(title));
        assertFalse(found, "Found non-existent title while testing");
        manager.searchByTitle(title);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    /**
     * Tests the search functionality with a non-existent author.
     * Verifies that the output is empty.
     */
    @Test
    void searchByNonExistentAuthorTest() {
        String author = "NonExistentAuthor";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getAuthor().equals(author));//extra check:)
        assertFalse(found, "Found non-existent author while testing");
        manager.searchByAuthor(author);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    /**
     * Tests the search functionality with a non-existent ISBN.
     * Verifies that the output is empty.
     */
    @Test
    void searchByNonExistentISBNTest() {
        String isbn = "0-0000-0000-0";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getIsbn().equals(isbn)); //extra check:)
        assertFalse(found, "Found non-existent ISBN while testing");
        manager.searchByISBN(isbn);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    /**
     * Teardown method executed after each test.
     * Resets the test environment.
     */
    @AfterEach
    public void clear() {
        manager.deletionBooks();
        manager.deletionBooksCopies();
        manager.deletionCustomers();
        System.setOut(originalOut);
        outContent.reset();
    }
}