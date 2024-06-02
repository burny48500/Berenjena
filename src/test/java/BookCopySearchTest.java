import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookCopySearchTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testSearchByTitle() {
        Book.creationBooks();
        BookCopy.creationBookCopies();
        String titleOfBook = "Berenjena";
        System.out.println("Enter a title: " + titleOfBook);
        BookCopy.searchByTitle(titleOfBook);

        String expectedOutput = "Enter a title: " + titleOfBook + System.lineSeparator() +
                "[Berenjena, Dr Pepper, 0-7642-1858-1, 1, A2, Available]" + System.lineSeparator() +
                "[Berenjena, Dr Pepper, 0-7642-1858-1, 2, B3, Available]"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSearchByAuthor() {
        outContent.reset();
        String author = "Dr Pepper";
        System.out.println("Enter an author: " + author);
        BookCopy.searchByAuthor(author);

        String expectedOutput = "Enter an author: " + author + System.lineSeparator() +
                "[Berenjena, Dr Pepper, 0-7642-1858-1, 1, A2, Available]" + System.lineSeparator() + "[Berenjena, Dr Pepper, 0-7642-1858-1, 2, B3, Available]" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSearchByISBN() {
        String isbn = "0-7642-1858-1";
        System.out.println("Enter an ISBN: " + isbn);
        BookCopy.searchByISBN(isbn);

        String expectedOutput = "Enter an ISBN: " + isbn + System.lineSeparator() +
                "[Berenjena, Dr Pepper, 0-7642-1858-1, 1, A2, Available]" + System.lineSeparator() +
                "[Berenjena, Dr Pepper, 0-7642-1858-1, 2, B3, Available]" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSearchNoResults() {
        String title = "Nonexistent Title";
        System.out.println("Enter a title: " + title);
        BookCopy.searchByTitle(title);

        String expectedOutput = "Enter a title: " + title + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testSearchBorrowedAndAvailable() {
        Customer.creationCustomers();
        BookCopy.borrow(3, 1);
        String titleBorrowedBook = "Tomatoes";
        System.out.println("Enter a title: " + titleBorrowedBook);
        BookCopy.searchByTitle(titleBorrowedBook);
        String output = outContent.toString().replace("\r", "");
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String expectedOutput = "Enter a title: " + titleBorrowedBook + System.lineSeparator() +
                "[Tomatoes, Ibañez, 0-7050-3533-6, 3, C7, Borrowed, " + formattedDate + "]" + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        BookCopy.returnBook(3, 1);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}