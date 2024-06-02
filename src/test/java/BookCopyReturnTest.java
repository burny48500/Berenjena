import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;
import prototype.commands.Customer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookCopyReturnTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testReturnBookSuccessfully() {
        // Prepare test data
        BookCopy.creationBookCopies();
        Customer.creationCustomers();
        int copyId = 1;
        int userId = 1;
        BookCopy.borrow(copyId, userId); // Borrow the copy to prepare for returning

        // Test returning a borrowed book copy
        outContent.reset(); // Clear the output stream
        BookCopy.returnBook(copyId, userId);
        assertEquals("Book copy returned successfully." + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testReturnBookNotBorrowed() {
        // Prepare test data
        BookCopy.creationBookCopies();
        int copyId = 1;
        int userId = 1;

        // Test trying to return a copy that was not borrowed
        outContent.reset(); // Clear the output stream
        BookCopy.returnBook(copyId, userId);
        assertTrue(outContent.toString().contains("The book is not borrowed."));
    }

    @Test
    public void testReturnBookNotExists() {
        BookCopy.creationBookCopies();
        int invalidCopyId = 100;
        int userId = 1;

        outContent.reset();
        BookCopy.returnBook(invalidCopyId, userId);
        assertTrue(outContent.toString().contains("Book copy does not exist."));
    }
}