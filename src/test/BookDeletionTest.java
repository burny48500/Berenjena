package test;

import org.junit.Test;
import prototype.commands.Book;
import prototype.commands.BookCopy;
import prototype.commands.Customer;

import static org.junit.Assert.*;

public class BookDeletionTest {
    @Test
    public void DeleteBookSuccessfullyTest() {
        Book bookToDelete = new Book("Frankenstein", "0-2456-4821-5", "Mary Shelley", "1818", "Science Fiction");
        Book book = new Book("Dune", "0-3756-9921-4", "Frank Herbert", "1965", "Science Fiction");
        Book.importBook("Dune", "0-3756-9921-4", "Frank Herbert", "1965", "Science Fiction");
        assertTrue(Book.getBooks().contains(bookToDelete));
        assertTrue(Book.getBooks().contains(bookToDelete));
        int bookSizeBeforeDeleting = Book.getBooks().size();
        Book.deleteBook("0-2456-4821-5");
        assertEquals(bookSizeBeforeDeleting - 1, Book.getBooks().size());
        assertTrue(Book.getBooks().contains(book));
        assertFalse(Book.getBooks().contains(bookToDelete));
    }

    @Test
    public void DeleteNotFoundBookTest() {
        Book book1 = new Book("The Lord of the Rings", "0-4956-9661-9", "J. R. R. Tolkien", "1954", "Fantasy");
        Book book2 = new Book("The Adventures of Sherlock Holmes", "0-1250-9821-7", "Arthur Conan Doyle", "1892", "Mystery");

        String nonExistentISBN = "0-0000-0000-0";
        boolean foundCount = false;
        for (String isbn : BookCopy.getCopyToBookMap().values()) {
            if (isbn.equals(nonExistentISBN)) {
                foundCount = true;
                break;
            }
        }
        assertFalse("Found non-existent ISBN during deletion test", foundCount); //extra check:)
        int bookSizeBeforeDeleting = Book.getBooks().size();
        Book.deleteBook(nonExistentISBN);
        assertEquals(bookSizeBeforeDeleting, Book.getBooks().size());
    }

    @Test
    public void DeleteBookWithBorrowedBookCopiesTest() {
        // Create a sample book
        Book book = new Book("The Great Gatsby", "0-4545-3215-8", "F. Scott Fitzgerald", "1925", "Social Commentary, Jazz Age");
        Customer customer = new Customer("urko.cornejo@tum.de", "0034640932256");
        BookCopy bookCopy = new BookCopy(book.getIsbn());

        Book.getBooks().add(book);
        BookCopy.borrow(bookCopy.getCopyId(), customer.getUserId(), "30 days");
        Book.deleteBook(book.getIsbn());
        assertTrue(Book.getBooks().contains(book));
    }
}