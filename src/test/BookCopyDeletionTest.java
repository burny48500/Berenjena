package test;

import org.junit.Test;
import prototype.commands.BookCopy;
import static org.junit.Assert.*;

public class BookCopyDeletionTest {
    @Test
    public void DeleteBookCopySuccessfullyTest() {
        BookCopy bookCopy = new BookCopy("0-8222-1328-6");
        int copyId = bookCopy.getCopyId();
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertFalse(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertFalse(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertNull(BookCopy.getBorrowStatus().get(copyId));
    }
    @Test
    public void DeleteNotFoundBookCopyTest() {
        int nonExistentId = 1042085073;

        boolean foundId = false;
        for (int id : BookCopy.getCopyToBookMap().keySet()) {
            if (id == nonExistentId) {
                foundId = true;
                break;
            }
        }
        assertFalse("Found non-existent copyID during deletion test", foundId); //just an extra check:)
        assertFalse(BookCopy.delete(nonExistentId));
    }
    @Test
    public void DeleteBorrowedBookCopyTest() {
        BookCopy bookCopy = new BookCopy("0-5678-8901-2");
        int copyId = bookCopy.getCopyId();
        BookCopy.getBorrowStatus().put(copyId, true);
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
    }
}