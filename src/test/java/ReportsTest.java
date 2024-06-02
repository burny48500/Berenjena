import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.commands.Reports;

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
        Book.creationBooks();
        BookCopy.creationBookCopies();
        Customer.creationCustomers();
        BookCopy.borrow(1, 1);
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
        ;
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAllNonBorrowedCopies() {
        Reports.allNonBorrowedCopies();
        String expectedOutput = "Title: Berenjena; Author: Dr Pepper; Year: 1980; ISBN: 0-7642-1858-1; Copy ID: 2" + System.lineSeparator() +
                "Title: Tomatoes; Author: Ibañez; Year: 2005; ISBN: 0-7050-3533-6; Copy ID: 3" + System.lineSeparator();
        ;
        assertEquals(expectedOutput, outContent.toString());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
    @Test
    public void testAllCustomers_Case1_PaidCustomerNoBooks() {
    // Case 1: Customer with paid status and no borrowed books
    Customer paidCustomerNoBooks = new Customer("Paid", "User", "paid@example.com", "123456789");
    paidCustomerNoBooks.setPaymentStatus(true);
    assertDoesNotThrow(() -> Reports.allCustomers());
}

    @Test
    public void testAllCustomers_Case2_UnpaidCustomerNoBooks() {
        // Case 2: Customer with unpaid status and no borrowed books
        Customer unpaidCustomerNoBooks = new Customer("Unpaid", "User", "unpaid@example.com", "987654321");
        unpaidCustomerNoBooks.setPaymentStatus(false);
        assertDoesNotThrow(() -> Reports.allCustomers());
    }

    @Test
    public void testAllCustomers_Case3_PaidCustomerWithBooks() {
        // Case 3: Customer with paid status and borrowed books
        Customer paidCustomerWithBooks = new Customer("Paid", "User", "paid_borrowed@example.com", "123123123");
        paidCustomerWithBooks.setPaymentStatus(true);
        BookCopy.borrow(1, paidCustomerWithBooks.getUserId());
        assertDoesNotThrow(() -> Reports.allCustomers());
    }

    @Test
    public void testAllCustomers_Case4_UnpaidCustomerWithBooks() {
        // Case 4: Customer with unpaid status and borrowed books
        Customer unpaidCustomerWithBooks = new Customer("Unpaid", "User", "unpaid_borrowed@example.com", "456456456");
        unpaidCustomerWithBooks.setPaymentStatus(false);
        BookCopy.borrow(2, unpaidCustomerWithBooks.getUserId());
        assertDoesNotThrow(() -> Reports.allCustomers());
        BookCopy.returnBook(2, unpaidCustomerWithBooks.getUserId());
    }
    @Test
    public void testBorrowedCustomer_Case1_ValidUserIdWithBorrowedBooks() {
        // Case 1: Valid user ID that has borrowed books
        int userIdWithBorrowedBooks = 1;
        BookCopy.borrow(1, userIdWithBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithBorrowedBooks));
    }

    @Test
    public void testBorrowedCustomer_Case2_ValidUserIdWithoutBorrowedBooks() {
        // Case 2: Valid user ID that has not borrowed any books
        int userIdWithoutBorrowedBooks = 2;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithoutBorrowedBooks));
    }

    @Test
    public void testBorrowedCustomer_Case3_InvalidUserId() {
        // Case 3: Invalid user ID
        int invalidUserId = -1;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(invalidUserId));
    }

    @Test
    public void testBorrowedCustomer_Case4_UserIdWithMultipleBorrowedBooks() {
        // Case 4: User ID that has borrowed multiple books
        int userIdWithMultipleBorrowedBooks = 3;
        BookCopy.borrow(2, userIdWithMultipleBorrowedBooks);
        BookCopy.borrow(3, userIdWithMultipleBorrowedBooks);
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithMultipleBorrowedBooks));
        BookCopy.returnBook(2, userIdWithMultipleBorrowedBooks);
        BookCopy.returnBook(3, userIdWithMultipleBorrowedBooks);
    }


}
