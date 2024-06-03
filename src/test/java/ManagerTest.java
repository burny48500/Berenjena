import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.commands.Manager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        Manager.creationBooks();
        Manager.creationBookCopies();
        Manager.creationCustomers();
        System.setOut(new PrintStream(outContent));
    }

    //BOOK
    @Test
    public void DeleteBookSuccessfullyTest() {
        Book bookToDelete = new Book("Frankenstein", "Mary Shelley", "0-2456-4821-5", "2007");
        Book book = new Book("Dune", "Frank Herbert", "0-3756-9921-4", "1972");
        assertTrue(Book.getBooks().contains(book));
        assertTrue(Book.getBooks().contains(bookToDelete));
        boolean deleted = Manager.deleteBook("0-2456-4821-5");
        assertTrue(deleted);
        assertTrue(Book.getBooks().contains(book));
        assertFalse(Book.getBooks().contains(bookToDelete));
    }

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
        Manager.deleteBook(nonExistentISBN);
        assertEquals(bookSizeBeforeDeleting, Book.getBooks().size());
    }

    @Test
    public void DeleteBookWithBorrowedBookCopiesTest() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "0-4545-3215-8", "1990");
        Customer customer = new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        BookCopy bookCopy = new BookCopy(book.getIsbn(), "B3");

        Book.getBooks().add(book);
        Manager.borrowBookCopy(bookCopy.getCopyId(), customer.getUserId());
        Manager.deleteBook(book.getIsbn());
        assertTrue(Book.getBooks().contains(book));
    }

    //BOOK COPY
        //BORROW TESTS
    @Test
    void borrowValidBookCopySuccessfullyTest() {
        int copyId = 1;
        int userId = 1;
        Manager.borrowBookCopy(copyId, userId);
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
    @Test
    void borrowNonExistentBookCopyTest() {
        int nonExistentId = 999999999;
        int userId = 1;
        Manager.borrowBookCopy(nonExistentId, userId);
        BookCopy nonExistentCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == nonExistentId)
                .findFirst()
                .orElse(null);

        assertNull(nonExistentCopy);
    }

    @Test
    void borrowAlreadyBorrowedBookCopyTest() {
        int copyId = 2;
        int userId = 1;
        Manager.borrowBookCopy(copyId, userId);
        BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == copyId)
                .findFirst()
                .orElse(null);

        assertNotNull(borrowedCopy);
        assertTrue(borrowedCopy.isBorrowed());
        assertEquals(userId, borrowedCopy.getUserId());

        int newUserId = 2;
        Manager.borrowBookCopy(copyId, newUserId);
        assertNotEquals(newUserId, borrowedCopy.getUserId());
        assertTrue(borrowedCopy.isBorrowed());
    }

    @Test
    void borrowBookCopyByNonExistentUserTest() {
        int copyId = 3;
        int nonExistentUserId = 999999999;
        Manager.borrowBookCopy(copyId, nonExistentUserId);
        BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == copyId)
                .findFirst()
                .orElse(null);

        assertNotNull(borrowedCopy);
        assertFalse(borrowedCopy.isBorrowed());
        assertEquals(-1, borrowedCopy.getUserId());
    }
    @Test
    void borrowBookCopyExceedingLimitTest() {
        new BookCopy("978-0201485677", "A2");
        new BookCopy("978-0201485677", "B3");
        new BookCopy("978-0201485677", "C9");
        new BookCopy("0-7050-3533-99", "D2");
        new BookCopy("0-7050-3533-6", "E1");
        new BookCopy("0-7050-3533-6", "C4");

        int userId = 1;
        for (int copyId = 1; copyId <= 5; copyId++) {
            final int finalCopyId = copyId;
            Manager.borrowBookCopy(finalCopyId, userId);
            BookCopy borrowedCopy = BookCopy.getBookCopies().stream()
                    .filter(copy -> copy.getCopyId() == finalCopyId)
                    .findFirst()
                    .orElse(null);

            assertNotNull(borrowedCopy);
            assertTrue(borrowedCopy.isBorrowed());
            assertEquals(userId, borrowedCopy.getUserId());
        }

        int newCopyId = 6;
        final int finalNewCopyId = newCopyId;
        Manager.borrowBookCopy(finalNewCopyId, userId);
        BookCopy newBorrowedCopy = BookCopy.getBookCopies().stream()
                .filter(copy -> copy.getCopyId() == finalNewCopyId)
                .findFirst()
                .orElse(null);

        assertNotNull(newBorrowedCopy);
        assertFalse(newBorrowedCopy.isBorrowed());
        assertEquals(-1, newBorrowedCopy.getUserId());
    }

        //DELETE TESTS
    @Test
    void deleteBookCopySuccessfullyTest() {
        BookCopy bookCopy = new BookCopy("0-7642-1858-1", "A2");
        int copyId = bookCopy.getCopyId();

        assertTrue(BookCopy.getBookCopies().contains(bookCopy));
        assertFalse(bookCopy.isBorrowed());

        assertTrue(Manager.deleteBookCopy(copyId));
        assertFalse(BookCopy.getBookCopies().contains(bookCopy));
    }

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
        assertFalse(Manager.deleteBookCopy(nonExistentId));
    }

    @Test
    void deleteBorrowedBookCopyTest() {
        BookCopy bookCopy = new BookCopy("0-5678-8901-2", "C2");
        int copyId = bookCopy.getCopyId();
        bookCopy.setBorrowed(true);
        assertTrue(bookCopy.isBorrowed());

        assertFalse(Manager.deleteBookCopy(copyId));
        assertTrue(BookCopy.getBookCopies().contains(bookCopy));
        assertTrue(bookCopy.isBorrowed());
    }

    //RETURN BOOK COPY

    @Test
    void returnBookCopySuccessfullyTest() {
        int userId = 1;
        int copyId = 1;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(userId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(10));

        Manager.returnBookCopy(copyId, userId);
        assertFalse(bookCopy.isBorrowed());
        assertEquals(-1, bookCopy.getUserId());
        assertEquals("Book copy (id = " + copyId + ") was returned successfully", outContent.toString().trim());
        assertTrue(Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == userId && c.getPaymentStatus()==0));
    }

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

        Manager.returnBookCopy(copyId, userId);
        assertEquals("The book copy (id = " + copyId + ") is not borrowed by that customer.", outContent.toString().trim());
        assertTrue(bookCopy.isBorrowed());
        assertEquals(wrongUserId, bookCopy.getUserId());
    }

    @Test
    void returnNonExistingBookCopyTest() {
        int userId = 1;
        int nonExistentCopyId = 999999;
        boolean found = BookCopy.getBookCopies().stream().anyMatch(bc -> bc.getCopyId() == nonExistentCopyId);
        assertFalse(found);
        Manager.returnBookCopy(nonExistentCopyId, userId);
        assertEquals("The book copy (id = " + nonExistentCopyId + ") does not exist.", outContent.toString().trim());
    }

    @Test
    void returnBookCopyWithNonExistingCustomerTest() {
        int nonExistentUserId = 999999;
        int copyId = 1;
        boolean found = Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == nonExistentUserId);
        assertFalse(found);
        Manager.returnBookCopy(copyId, nonExistentUserId);
        assertEquals("No customer with (id = " + nonExistentUserId + ") exists.", outContent.toString().trim());
    }

    @Test
    void returnBookCopyExceedingBorrowingTimeTest() {
        int userId = 1;
        int copyId = 1;
        BookCopy bookCopy = BookCopy.getBookCopies().stream().filter(bc -> bc.getCopyId() == copyId).findFirst().orElse(null);
        assertNotNull(bookCopy);
        bookCopy.setUserId(userId);
        bookCopy.setBorrowed(true);
        bookCopy.setBorrowedDate(LocalDate.now().minusDays(40));

        Manager.returnBookCopy(copyId, userId);
        assertEquals("The maximum borrowing time is exceeded. Book returned successfully", outContent.toString().trim());
        assertFalse(bookCopy.isBorrowed());
        assertEquals(-1, bookCopy.getUserId());
        assertTrue(Customer.getCustomers().stream().anyMatch(c -> c.getUserId() == userId && c.getPaymentStatus()==0));
    }

    //CUSTOMER

    @Test
    void deletingCustomerWithBorrowedBooksTest() {
        Customer customer = new Customer("Cid", "Miguel", "miguel@tum.de", "640882288");
        BookCopy bookCopy = new BookCopy("0-5678-8901-2", "C2");
        Manager.borrowBookCopy(1, 1);
        int customerId = 1;
        Manager.deleteCustomer(customerId);
        assertTrue(Manager.customerExistsTests(customerId));
    }

    @Test
    void deletingCustomerWithNoBorrowedBooksTest() {
        int customerId = 2;
        Manager.deleteCustomer(customerId);
        assertFalse(Manager.customerExistsTests(customerId), "not deleted");
    }

    @Test
    void deletingNonExistingCustomerTest() {
        int userId = Customer.getNextId() + 1;
        Manager.deleteCustomer(userId);
        assertFalse(Manager.customerExistsTests(userId));
    }

    // SEARCH TESTS
    @Test
    void searchByTitleTest() {
        String title = "Tomatoes";
        Manager.searchByTitle(title);
        String output = outContent.toString().trim();
        assertTrue(output.contains("Tomatoes"));
    }

    @Test
    void searchByAuthorTest() {
        String author = "Dr Pepper";
        Manager.searchByAuthor(author);
        String output = outContent.toString().trim();
        assertFalse(output.isEmpty());
        assertTrue(output.contains(author));
    }

    @Test
    void searchByISBNTest() {
        String isbn = "0-7642-1858-1";
        Manager.searchByISBN(isbn);
        String output = outContent.toString().trim();
        assertFalse(output.isEmpty());
        assertTrue(output.contains(isbn));
    }

    @Test
    void searchByNonExistentTitleTest() {
        String title = "NonExistentTitle";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getTitle().equals(title));
        assertFalse(found, "Found non-existent title while testing");
        Manager.searchByTitle(title);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    @Test
    void searchByNonExistentAuthorTest() {
        String author = "NonExistentAuthor";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getAuthor().equals(author));//extra check:)
        assertFalse(found, "Found non-existent author while testing");
        Manager.searchByAuthor(author);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    @Test
    void searchByNonExistentISBNTest() {
        String isbn = "0-0000-0000-0";
        boolean found = Book.getBooks().stream().anyMatch(book -> book.getIsbn().equals(isbn)); //extra check:)
        assertFalse(found, "Found non-existent ISBN while testing");
        Manager.searchByISBN(isbn);
        String output = outContent.toString().trim();
        assertTrue(output.isEmpty());
    }

    @AfterEach
    public void clear() {
        Manager.deletionBooks();
        Manager.deletionBooksCopies();
        Manager.deletionCustomers();
        System.setOut(originalOut);
        outContent.reset();
    }
}