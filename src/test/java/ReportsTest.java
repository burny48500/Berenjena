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
    }

    @Test
    public void testAllBooks() {
        Manager.creationBooks();
        Manager.creationBookCopies();
        Manager.creationCustomers();
        Manager.borrowBookCopy(1, 1);
        outContent.reset();
        Reports.allBooks();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllBorrowedCopies() {
        outContent.reset();
        Reports.allBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllNonBorrowedCopies() {
        outContent.reset();
        Reports.allNonBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 2" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6; Copy ID: 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllCustomers_Case1_PaidCustomerNoBooks() {
        Customer paidCustomerNoBooks = new Customer("Cid", "Miguel", "miguel.cid@tum.de", "0034640882288");
        paidCustomerNoBooks.setPaymentStatus(true);
        assertDoesNotThrow(() -> Reports.allCustomers());
    }

    @Test
    public void testAllCustomers_Case2_UnpaidCustomerNoBooks() {
        Customer unpaidCustomerNoBooks = new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        unpaidCustomerNoBooks.setPaymentStatus(false);
        assertDoesNotThrow(() -> Reports.allCustomers());
    }

    @Test
    public void testAllCustomers_Case3_PaidCustomerWithBooks() {
        Customer paidCustomerWithBooks = new Customer("Paid", "User", "paid_borrowed@example.com", "123123123");
        paidCustomerWithBooks.setPaymentStatus(true);
        Manager.borrowBookCopy(1, paidCustomerWithBooks.getUserId());
        assertDoesNotThrow(() -> Reports.allCustomers());
        Manager.returnBookCopy(1, paidCustomerWithBooks.getUserId());
    }

    @Test
    public void testAllCustomers_Case4_UnpaidCustomerWithBooks() {
        Customer unpaidCustomerWithBooks = new Customer("Unpaid", "User", "unpaid_borrowed@example.com", "456456456");
        unpaidCustomerWithBooks.setPaymentStatus(false);
        Manager.borrowBookCopy(2, unpaidCustomerWithBooks.getUserId());
        assertDoesNotThrow(() -> Reports.allCustomers());
        Manager.returnBookCopy(2, unpaidCustomerWithBooks.getUserId());
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
    }
}