import org.junit.jupiter.api.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.commands.Reports;

import static org.junit.jupiter.api.Assertions.*;
public class ReportTest {

    @Test
    public void testAllCustomers() {
        Customer.creationCustomers();
        Book.creationBooks();
        BookCopy.creationBookCopies();

        // Case 1: Customer with paid status and no borrowed books
        Customer paidCustomerNoBooks = new Customer("Paid", "User", "paid@example.com", "123456789");
        paidCustomerNoBooks.setPaymentStatus(true);

        // Case 2: Customer with unpaid status and no borrowed books
        Customer unpaidCustomerNoBooks = new Customer("Unpaid", "User", "unpaid@example.com", "987654321");
        unpaidCustomerNoBooks.setPaymentStatus(false);

        // Case 3: Customer with paid status and borrowed books
        Customer paidCustomerWithBooks = new Customer("Paid", "User", "paid_borrowed@example.com", "123123123");
        paidCustomerWithBooks.setPaymentStatus(true);
        BookCopy.borrow(1, paidCustomerWithBooks.getUserId());

        // Case 4: Customer with unpaid status and borrowed books
        Customer unpaidCustomerWithBooks = new Customer("Unpaid", "User", "unpaid_borrowed@example.com", "456456456");
        unpaidCustomerWithBooks.setPaymentStatus(false);
        BookCopy.borrow(2, unpaidCustomerWithBooks.getUserId());

        assertDoesNotThrow(() -> Reports.allCustomers());

    }

    @Test
    public void testBorrowedCustomer() {
        // Case 1: Valid user ID that has borrowed books
        int userIdWithBorrowedBooks = 1;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithBorrowedBooks));


        // Case 2: Valid user ID that has not borrowed any books
        int userIdWithoutBorrowedBooks = 2;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithoutBorrowedBooks));


        // Case 3: Invalid user ID
        int invalidUserId = -1;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(invalidUserId));


        // Case 4: User ID that has borrowed multiple books
        int userIdWithMultipleBorrowedBooks = 3;
        assertDoesNotThrow(() -> Reports.BorrowedCustomer(userIdWithMultipleBorrowedBooks));
    }
}
