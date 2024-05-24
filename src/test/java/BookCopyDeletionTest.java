import org.junit.jupiter.api.Test;
import prototype.commands.BookCopy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookCopyDeletionTest {
    @Test
    void DeleteBookCopySuccessfullyTest() {
        BookCopy bookCopy = new BookCopy("0-8222-1328-6");
        int copyId = bookCopy.getCopyId();
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertFalse(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertFalse(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertNull(BookCopy.getBorrowStatus().get(copyId));
    }
    @Test
    void DeleteNotFoundBookCopyTest() {
        int nonExistentId = 1042085073;

        boolean foundId = false;
        for (int id : BookCopy.getCopyToBookMap().keySet()) {
            if (id == nonExistentId) {
                foundId = true;
                break;
            }
        }
        assertFalse(foundId, "Found non-existent copyID during deletion test"); //just an extra check:)
        assertFalse(BookCopy.delete(nonExistentId));
    }
    @Test
    void DeleteBorrowedBookCopyTest() {
        BookCopy bookCopy = new BookCopy("0-5678-8901-2");
        int copyId = bookCopy.getCopyId();
        BookCopy.getBorrowStatus().put(copyId, true);
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
        BookCopy.delete(copyId);
        assertTrue(BookCopy.getCopyToBookMap().containsKey(copyId));
        assertTrue(BookCopy.getBorrowStatus().get(copyId));
    }
}
