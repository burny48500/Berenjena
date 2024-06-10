import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class ImporterTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        Importer.setDirectory(tempFile.getParent());
        Importer.setFilename(tempFile.getName());
        Importer.setTestMode(true);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
        Importer.setTestMode(false);
    }

    @Test
    void testImportBook() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Title,Author,ISBN,Year\n");
            writer.write("Effective Java,Joshua Bloch,978-0134685991,1987\n");
        }
        Importer.importBook();
        assertTrue(Book.sameBook("978-0134685991"));
    }


    @Test
    void testImportBookCopyAlreadyOnLoan() throws IOException {
        Customer customer = new Customer("firstName", "lastName", "first.last@tum.de", "123456");
        new Book("Refactoring", "Martin Fowler","978-0201485677", "1993");
        String customerId = String.valueOf(customer.getUserId());

        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,Publisher,CustomerId\n");
            writer.write("978-0201485677,A2,LibrosPeter,"+ customerId + "\n");
        }
        Importer.importBookCopy();
        assertTrue(Book.sameBook("978-0201485677"));
        for (BookCopy bookCopy: BookCopy.getBookCopies()) {
            if (bookCopy.getIsbn().equals("978-0201485677")) {
                assertTrue(bookCopy.isBorrowed());  // Checks whether the book was succesfully imported as already on loan
                assertEquals(bookCopy.getUserId(), customer.getUserId());
            }
        }
    }

    @Test
    void testImportBookCopyNotOnLoan() throws IOException {
        Customer customer = new Customer("firstName", "lastName", "first.last@tum.de", "123456");
        new Book("Refactoring", "Martin Fowler","978-0201485677", "1993");

        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,Publisher,CustomerId\n");
            writer.write("978-0201485677,A2,LibrosPeter,-1\n");
        }
        Importer.importBookCopy();
        assertTrue(Book.sameBook("978-0201485677"));
        for (BookCopy bookCopy: BookCopy.getBookCopies()) {
            if (bookCopy.getIsbn().equals("978-0201485677")) {
                assertFalse(bookCopy.isBorrowed());  // Checks whether the book was succesfully imported not on loan
                assertEquals(bookCopy.getUserId(), -1);
            }
        }
    }

    @Test
    void testImportCustomer() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Name,First Name,Mail,Phone Number\n");
            writer.write("Cid,Miguel,miguel.cid@tum.de,123-456-7890\n");
        }
        Importer.importCustomer();
        assertTrue(Customer.sameCustomer("miguel.cid@tum.de"));
    }

    @Test
    void testImportBookWithIncorrectCSV() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("IncorrectHeader1,IncorrectHeader2,IncorrectHeader3,IncorrectHeader4\n");
            writer.write("Test Book,123456789,Test Author,Hola\n");
        }
        Importer.importBook();
    }

    @Test
    void testImportBookCopyWithNonExistentBook() throws IOException {
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("ISBN,Shelf Location,LibrosPeter\n");
            writer.write("0-7050-3533-99,NonExistent Shelf,Anaya\n");
        }

        Importer.importBookCopy();
    }

    @Test
    void testImportCustomerWithDuplicate() throws IOException {
        new Customer("Cid", "Miguel", "miguel.cid@tum.de", "0034640882288");
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("Name,First Name,Mail,Phone Number\n");
            writer.write("Cid,Miguel,miguel.cid@tum.de,123-456-7890\n");
        }
        Importer.importCustomer();
    }
}
