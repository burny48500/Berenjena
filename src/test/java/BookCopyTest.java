import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;
import prototype.commands.Customer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookCopyTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @BeforeEach
    void setUp() {
        BookCopy.creationBookCopies();
        Customer.creationCustomers();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void borrowValidBookCopySuccessfullyTest() {
        int copyId = 1;
        int userId = 1;
        BookCopy.borrow(copyId, userId);
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
        assertEquals(userId, BookCopy.getBorrowerId(copyId));

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        assertEquals(formattedDate, BookCopy.getBorrowDates().get(copyId));
    }

    @Test
    void borrowNonExistentBookCopyTest() {
        int nonExistentId = 999999999;
        int userId = 1;
        BookCopy.borrow(nonExistentId, userId);
        assertFalse(BookCopy.getBorrowStatus().containsKey(nonExistentId));
        assertNull(BookCopy.getCopyBorrowers().get(nonExistentId));
        assertNull(BookCopy.getBorrowDates().get(nonExistentId));
    }

    @Test
    void borrowAlreadyBorrowedBookCopyTest() {
        int copyId = 2;
        int userId = 1;
        BookCopy.borrow(copyId, userId);
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
        assertEquals(userId, BookCopy.getBorrowerId(copyId));

        int newUserId = 2;
        BookCopy.borrow(copyId, newUserId);
        assertNotEquals(newUserId, BookCopy.getBorrowerId(copyId));
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
    }

    @Test
    void borrowBookCopyByNonExistentUserTest() {
        int copyId = 3;
        int nonExistentUserId = 999999999;
        BookCopy.borrow(copyId, nonExistentUserId);
        assertFalse(BookCopy.getBorrowStatus().get(copyId));
        assertNull(BookCopy.getCopyBorrowers().get(copyId));
        assertNull(BookCopy.getBorrowDates().get(copyId));
    }

    @Test
    void borrowBookCopyExceedingLimitTest() {
        // Creating book copies with their respective shelf locations
        new BookCopy("978-0201485677", "A2");
        new BookCopy("978-0201485677", "B3");
        new BookCopy("978-0201485677", "C9");
        new BookCopy("0-7050-3533-99", "D2");
        new BookCopy("0-7050-3533-6", "E1");
        new BookCopy("0-7050-3533-6", "C4");

        int userId = 1;
        for (int copyId = 1; copyId <= 5; copyId++) {
            BookCopy.borrow(copyId, userId);
            assertTrue(BookCopy.getBorrowStatus().get(copyId));
            assertEquals(userId, BookCopy.getBorrowerId(copyId));
        }

        int newCopyId = 6;
        BookCopy.borrow(newCopyId, userId);

        assertFalse(BookCopy.getBorrowStatus().get(newCopyId));
        assertNull(BookCopy.getCopyBorrowers().get(newCopyId));
        assertNull(BookCopy.getBorrowDates().get(newCopyId));
    }

    @Test
    public void testReturnBookNotBorrowed() {
        BookCopy.creationBookCopies();
        Customer.creationCustomers();
        int copyId = 1;
        int userId = 1;

        outContent.reset();
        BookCopy.returnBook(copyId, userId);
        assertEquals("The book is not borrowed." + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testReturnBookSuccessfully() {
        int copyId = 1;
        int userId = 1;
        BookCopy.borrow(copyId, userId);

        outContent.reset();
        BookCopy.returnBook(copyId, userId);
        assertEquals("Book copy returned successfully." + System.lineSeparator(), outContent.toString());
        BookCopy.returnBook(1, 1);
    }


    @Test
    public void testReturnBookNotExists() {
        int invalidCopyId = 999999999;
        int userId = 1;

        boolean found = false;
        for (Integer copyId : BookCopy.getCopyToBookMap().keySet()) {
            if (copyId == invalidCopyId) {
                found = true;
                break;
            }
        }
        assertFalse(found, "Found invalid copy ID"); //extra check:)
        BookCopy.returnBook(invalidCopyId, userId);
        assertTrue(outContent.toString().contains("Book copy does not exist."));
    }
    @Test
    void DeleteBookCopySuccessfullyTest() {
        BookCopy bookCopy = new BookCopy("0-8222-1328-6", "B3");
        int copyId = bookCopy.getCopyId();
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertFalse(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertFalse(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertNull(BookCopy.getBorrowStatus().get(copyId));
    }
    @Test
    void DeleteNotFoundBookCopyTest() {
        int nonExistentId = 1042085073;

        boolean foundId = false;
        for (int id : BookCopy.getCopyToBookMap().keySet()) {
            if (id == nonExistentId) {
                foundId = true;
                break;
            }
        }
        assertFalse(foundId, "Found non-existent copyID during deletion test"); //just an extra check:)
        assertFalse(BookCopy.delete(nonExistentId));
    }
    @Test
    void DeleteBorrowedBookCopyTest() {
        BookCopy bookCopy = new BookCopy("0-5678-8901-2", "C2");
        int copyId = bookCopy.getCopyId();
        BookCopy.getBorrowStatus().put(copyId, true);
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
    }
    @AfterEach
    public void reset() {
        outContent.reset();
    }
}