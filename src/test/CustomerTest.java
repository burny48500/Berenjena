package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.CommandParser;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.prompt.Prompter;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @BeforeEach
    void setUp() {
        Customer.creationCustomers();
        BookCopy.creationBookCopies();
    }

    @Test
    void deletingCustomerWithBorrowedBooksTest() {
        BookCopy.borrow(1, 1, "7");
        int customerId= 1;
        Customer.delete(customerId);
        assertTrue(Customer.customerExists(customerId));
    }
    @Test
    void deletingCustomerWithNoBorrowedBooksTest() {
        int customerId= 2;
        Customer.delete(customerId);
        assertFalse(Customer.customerExists(customerId),"not deleted");
    }
    @Test
    void deletingNonExistingCustomerTest() {
        Customer.delete(999);
        assertFalse(Customer.customerExists(999));
    }
}
