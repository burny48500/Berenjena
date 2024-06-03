import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        Manager.creationBooks();
        Manager.creationBookCopies();
        Manager.creationCustomers();
    }

    @Test
    public void testAllBooks() {
        Manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allBooks();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllBorrowedCopies() {
        Manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllNonBorrowedCopies() {
        outContent.reset();
        Reports.allNonBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 1" + System.lineSeparator() + "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 2" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6; Copy ID: 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllCustomers() {
        Manager.borrowBookCopy(1, 1);
        Manager.returnBookCopy(1,1);
        Manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allCustomers();
        String expectedOutput = "User ID: 1; First Name: Miguel; Name: Cid; Payment Status: Paid; Number of Books Currently Borrowed: 1\n" +
                "User ID: 2; First Name: Urko; Name: Cornejo; Payment Status: Not Paid; Number of Books Currently Borrowed: 0\n" ;
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testBorrowedCustomer_Case1_ValidUserIdWithBorrowedBooks() {
        int userIdWithBorrowedBooks = 1;
        Manager.borrowBookCopy(1, userIdWithBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithBorrowedBooks));
    }

    @Test
    public void testBorrowedCustomer_Case2_ValidUserIdWithoutBorrowedBooks() {
        int userIdWithoutBorrowedBooks = 2;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithoutBorrowedBooks));
    }

    @Test
    public void testBorrowedCustomer_Case3_InvalidUserId() {
        int invalidUserId = -1;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(invalidUserId));
    }

    @Test
    public void testBorrowedCustomer_Case4_UserIdWithMultipleBorrowedBooks() {
        int userIdWithMultipleBorrowedBooks = 3;
        Manager.borrowBookCopy(2, userIdWithMultipleBorrowedBooks);
        Manager.borrowBookCopy(2, userIdWithMultipleBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithMultipleBorrowedBooks));
        Manager.returnBookCopy(2, userIdWithMultipleBorrowedBooks);
        Manager.returnBookCopy(3, userIdWithMultipleBorrowedBooks);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        Manager.deletionBooks();
        Manager.deletionBooksCopies();
        Manager.deletionCustomers();
    }
}