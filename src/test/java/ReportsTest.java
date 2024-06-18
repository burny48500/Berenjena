import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing the functionality of the Reports class.
 */
public class ReportsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Manager manager = new Manager();

    /**
     * Setup method executed before each test.
     * Redirects console output and initializes test data.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        manager.creationBooks();
        manager.creationBookCopies();
        manager.creationCustomers();
    }

    /**
     * Tests the 'allBooks' method of the Reports class.
     * Verifies the output when all books are listed.
     */
    @Test
    public void testAllBooks() {
        manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allBooks();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the 'allBorrowedCopies' method of the Reports class.
     * Verifies the output when all borrowed copies are listed.
     */
    @Test
    public void testAllBorrowedCopies() {
        manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the functionality to retrieve all non-borrowed copies of books.
     * Verifies that the output contains information about all non-borrowed copies.
     */
    @Test
    public void testAllNonBorrowedCopies() {
        outContent.reset();
        Reports.allNonBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 1" + System.lineSeparator() + "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 2" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6; Copy ID: 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the functionality to retrieve information about all customers.
     * Verifies that the output contains information about all customers and their borrowed books.
     */
    @Test
    public void testAllCustomers() {
        manager.borrowBookCopy(1, 1);
        manager.returnBookCopy(1, 1);
        manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allCustomers();
        String expectedOutput = "User ID: 1; First Name: Miguel; Name: Cid; Payment Status: No fee; Number of Books Currently Borrowed: 1" + System.lineSeparator() +
                "User ID: 2; First Name: Urko; Name: Cornejo; Payment Status: No fee; Number of Books Currently Borrowed: 0" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the functionality to retrieve information about a borrowed customer.
     * Verifies that the method does not throw an exception for valid and invalid user IDs.
     */
    @Test
    public void testBorrowedCustomer_Case1_ValidUserIdWithBorrowedBooks() {
        int userIdWithBorrowedBooks = 1;
        manager.borrowBookCopy(1, userIdWithBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithBorrowedBooks));
    }

    /**
     * Tests the functionality to retrieve information about a borrowed customer with no borrowed books.
     * Verifies that the method does not throw an exception for a valid user ID with no borrowed books.
     */
    @Test
    public void testBorrowedCustomer_Case2_ValidUserIdWithoutBorrowedBooks() {
        int userIdWithoutBorrowedBooks = 2;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithoutBorrowedBooks));
    }

    /**
     * Tests the functionality to retrieve information about a borrowed customer with an invalid user ID.
     * Verifies that the method does not throw an exception for an invalid user ID.
     */
    @Test
    public void testBorrowedCustomer_Case3_InvalidUserId() {
        int invalidUserId = -1;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(invalidUserId));
    }

    /**
     * Tests the functionality to retrieve information about a customer with multiple borrowed books.
     * Verifies that the method does not throw an exception for a valid user ID with multiple borrowed books.
     */
    @Test
    public void testBorrowedCustomer_Case4_UserIdWithMultipleBorrowedBooks() {
        int userIdWithMultipleBorrowedBooks = 3;
        manager.borrowBookCopy(2, userIdWithMultipleBorrowedBooks);
        manager.borrowBookCopy(2, userIdWithMultipleBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithMultipleBorrowedBooks));
        manager.returnBookCopy(2, userIdWithMultipleBorrowedBooks);
        manager.returnBookCopy(3, userIdWithMultipleBorrowedBooks);
    }

    /**
     * Tests the functionality to retrieve the number of book copies per publisher.
     * Verifies that the output contains the expected information about book copies per publisher.
     */
    @Test
    public void testNumberOfBookCopiesPerPublisher() {
        outContent.reset();
        Reports.NumberOfBookCopiesPerPublisher();
        String expectedOutput = "Anaya: 1 book copy (33.3%)" + System.lineSeparator() +
                "Caramin: 1 book copy (33.3%)" + System.lineSeparator() +
                "LibrosPeter: 1 book copy (33.3%)" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests the functionality to retrieve the number of book copies per publisher when there are no copies.
     * Verifies that the output indicates that no book copies were found.
     */
    @Test
    public void testNumberOfBookCopiesPerPublisher_WithoutCopies() {
        outContent.reset();
        manager.deletionBooksCopies();
        Reports.NumberOfBookCopiesPerPublisher();
        String expectedOutput = "No book copies found.";
        boolean check = outContent.toString().contains(expectedOutput);
        assertTrue(check);
    }

    /**
     * Teardown method executed after each test.
     * Resets console output and deletes test data.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        manager.deletionBooks();
        manager.deletionBooksCopies();
        manager.deletionCustomers();
    }
}