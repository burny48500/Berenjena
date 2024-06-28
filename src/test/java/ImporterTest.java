import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the importer class.
 * Tests the functionality of importing books, book copies, and customers from CSV files.
 */
class importerTest {

    private File tempFile;
    private Importer importer;
    private Book book;

    /**
     * Sets up the test environment by creating a temporary file.
     *
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        importer.setDirectory(tempFile.getParent());
        importer.setFilename(tempFile.getName());
        importer.setTestMode(true);
    }

    /**
     * Tears down the test environment by deleting the temporary file.
     */
    @AfterEach
    void tearDown() {
        tempFile.delete();
        importer.setTestMode(false);
    }

    /**
     * Tests the importBook method by writing a book record to a CSV file and verifying its import.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportBook() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Title,Author,ISBN,Year\n");
            writer.write("Effective Java,Joshua Bloch,978-0134685991,1987\n");
        }
        importer.importBook();
        assertTrue(Book.sameBook("978-0134685991"));
    }

    /**
     * Tests importing a book copy that is already borrowed.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportBookCopyAlreadyOnLoan() throws IOException {
        Customer customer = new Customer("firstName", "lastName", "first.last@tum.de", "123456");
        new Book("Refactoring", "Martin Fowler", "978-0201485677", "1993");
        String customerId = String.valueOf(customer.getUserId());

        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,Publisher,CustomerId\n");
            writer.write("978-0201485677,A2,LibrosPeter," + customerId + "\n");
        }
        importer.importBookCopy();
        assertTrue(Book.sameBook("978-0201485677"));
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.getIsbn().equals("978-0201485677")) {
                assertTrue(bookCopy.isBorrowed());  // Checks whether the book was successfully imported as already on loan
                assertEquals(bookCopy.getUserId(), customer.getUserId());
            }
        }
    }

    /**
     * Tests importing a book copy that is not borrowed.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportBookCopyNotOnLoan() throws IOException {
        Customer customer = new Customer("firstName", "lastName", "first.last@tum.de", "123456");
        new Book("Refactoring", "Martin Fowler", "978-0201485677", "1993");

        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,Publisher,CustomerId\n");
            writer.write("978-0201485677,A2,LibrosPeter,-1\n");
        }
        importer.importBookCopy();
        assertTrue(Book.sameBook("978-0201485677"));
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.getIsbn().equals("978-0201485677")) {
                assertFalse(bookCopy.isBorrowed());  // Checks whether the book was successfully imported not on loan
                assertEquals(bookCopy.getUserId(), -1);
            }
        }
    }

    /**
     * Tests importing a customer.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportCustomer() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Name,First Name,Mail,Phone Number\n");
            writer.write("Cid,Miguel,miguel.cid@tum.de,123-456-7890\n");
        }
        importer.importCustomer();
        assertTrue(Customer.sameCustomer("miguel.cid@tum.de"));
    }

    /**
     * Tests importing a book with incorrect CSV headers.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportBookWithIncorrectCSV() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("IncorrectHeader1,IncorrectHeader2,IncorrectHeader3,IncorrectHeader4\n");
            writer.write("Test Book,123456789,Test Author,Hola\n");
        }
        importer.importBook();
    }

    /**
     * Tests importing a book copy with a non-existent book.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportBookCopyWithNonExistentBook() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,LibrosPeter\n");
            writer.write("0-7050-3533-99,NonExistent Shelf,Anaya\n");
        }

        importer.importBookCopy();
    }

    /**
     * Tests importing a customer with a duplicate entry.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testImportCustomerWithDuplicate() throws IOException {
        new Customer("Cid", "Miguel", "miguel.cid@tum.de", "0034640882288");
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Name,First Name,Mail,Phone Number\n");
            writer.write("Cid,Miguel,miguel.cid@tum.de,123-456-7890\n");
        }
        importer.importCustomer();
    }
}