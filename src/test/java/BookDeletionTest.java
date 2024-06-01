import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;
import prototype.commands.Book;
import prototype.commands.Customer;
import prototype.commands.Manager;

import static org.junit.jupiter.api.Assertions.*;

public class BookDeletionTest {
    @Test
    public void DeleteBookSuccessfullyTest() {
        Book bookToDelete = new Book("Frankenstein", "0-2456-4821-5", "Mary Shelley", "2007");
        Book book = new Book("Dune", "0-3756-9921-4", "Frank Herbert", "1972");
        //Book.importBook("Dune", "0-3756-9921-4", "Frank Herbert", "1965", "Science Fiction");
        assertTrue(Book.getBooks().contains(bookToDelete));
        assertTrue(Book.getBooks().contains(bookToDelete));
        int bookSizeBeforeDeleting = Book.getBooks().size();
        Manager.deleteBook("0-2456-4821-5");
        assertEquals(bookSizeBeforeDeleting - 1, Book.getBooks().size());
        assertTrue(Book.getBooks().contains(book));
        assertFalse(Book.getBooks().contains(bookToDelete));
    }

    @Test
    public void DeleteNotFoundBookTest() {
        Book book1 = new Book("The Lord of the Rings", "0-4956-9661-9", "J. R. R. Tolkien", "1976");
        Book book2 = new Book("The Adventures of Sherlock Holmes", "0-1250-9821-7", "Arthur Conan Doyle", "2002");

        String nonExistentISBN = "0-0000-0000-0";
        boolean foundCount = false;
        for (Book book : Book.getBooks()) {
            if (book.getIsbn().equals(nonExistentISBN)) {
                foundCount = true;
                break;
            }
        }
        assertFalse(foundCount, "Found non-existent ISBN during deletion test"); //extra check:)
        int bookSizeBeforeDeleting = Book.getBooks().size();
        Manager.deleteBook(nonExistentISBN);
        assertEquals(bookSizeBeforeDeleting, Book.getBooks().size());
    }

    @Test
    public void DeleteBookWithBorrowedBookCopiesTest() {
        // Create a sample book
        Book book = new Book("The Great Gatsby", "0-4545-3215-8", "F. Scott Fitzgerald", "1990");
        Customer customer = new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        BookCopy bookCopy = new BookCopy(book.getIsbn(), "B3");

        Book.getBooks().add(book);
        Manager.borrowBook(bookCopy.getCopyId(), customer.getUserId());
        Manager.deleteBook(book.getIsbn());
        assertTrue(Book.getBooks().contains(book));
    }
}
