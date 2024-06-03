import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;
import prototype.commands.Customer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BookCopyReturnTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
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
        int invalidCopyId = 100;
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
    @AfterEach
    public void reset() {
        outContent.reset();
    }
}