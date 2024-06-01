import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;
import prototype.commands.Customer;
import prototype.commands.Manager;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @BeforeEach
    void setUp() {
        Manager.creationCustomers();
        Manager.creationBookCopies();
    }

    @Test
    void deletingCustomerWithBorrowedBooksTest() {
        Manager.borrowBook(1, 1);
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
        Manager.deleteCustomer(999);
        assertFalse(Manager.customerExistsTests(999));
    }
}

