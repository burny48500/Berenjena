package prototype.commands;

import prototype.prompt.Prompter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The Manager class provides methods for managing books, book copies, and customers.
 * It includes functionality for creating, deleting, borrowing, and returning books,
 * as well as searching for books and managing customers.
 */
public class Manager {

    // CREATION AND DELETION OF BOOKS;BOOK COPIES;CUSTOMERS

    /**
     * Creates some books.
     */
    public void creationBooks() {
        new Book("Berenjena", "Dr Pepper", "0-7642-1858-1", "1980");
        new Book("Tomatoes", "Ibañez", "0-7050-3533-6", "2005");
    }

    /**
     * Deletes all books by clearing the book list and resetting the book ID counter.
     */
    public void deletionBooks() {
        Book.setBooks(new ArrayList<>());
        Book.setNextBookId(1);
    }

    /**
     * Creates some book copies.
     */
    public void creationBookCopies() {
        new BookCopy("0-7642-1858-1", "A2", "Caramin");
        new BookCopy("0-7642-1858-1", "B3", "LibrosPeter");
        new BookCopy("0-7050-3533-6", "C7", "Anaya");
    }

    /**
     * Deletes all book copies by clearing the book copy list and resetting the book copy ID counter.
     */
    public void deletionBooksCopies() {
        BookCopy.setBookCopies(new ArrayList<>());
        BookCopy.setNextBookId(1);
    }

    /**
     * Creates some customers.
     */
    public void creationCustomers() {
        new Customer("Cid", "Miguel", "miguel.cid@tum.de", "0034640882288");
        new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        Customer.setText(true);
    }

    /**
     * Deletes all customers by clearing the customer list and resetting the customer ID counter.
     */
    public void deletionCustomers() {
        Customer.setCustomers(new ArrayList<>());
        Customer.setNextId(1);
    }

    /**
     * Counts the number of books currently borrowed by a customer.
     *
     * @param userId the ID of the customer
     * @return the number of books borrowed by the customer
     */
    private int amountOfBooksPerCustomer(int userId) {
        int count = 0;
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.getUserId() == userId) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Checks if a customer exists by their ID.
     *
     * @param userId the ID of the customer
     * @return the customer if found, otherwise null
     */
    public Customer customerExists(int userId) {
        for (Customer customer : Customer.getCustomers()) {
            if (customer.getUserId() == userId) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Checks if a customer exists by their ID.
     *
     * @param userId the ID of the customer
     * @return true if the customer exists, otherwise false
     */
    public boolean customerExistsTests(int userId) {
        for (Customer customer : Customer.getCustomers()) {
            if (customer.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    // BORROW AND RETURN OF A BOOK COPY

    /**
     * Borrows a book copy for a customer if the customer is eligible and the book copy is available.
     *
     * @param copyId the ID of the book copy
     * @param userId the ID of the customer
     */
    public void borrowBookCopy(int copyId, int userId) {
        if (amountOfBooksPerCustomer(userId) < 5 && customerExistsTests(userId)) {
            for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                if (bookCopy.getCopyId() == copyId) {
                    if (!bookCopy.isBorrowed()) {
                        bookCopy.setBorrowedDate(LocalDate.now());
                        bookCopy.setBorrowed(true);
                        bookCopy.setUserId(userId);
                        System.out.println("Book copy (id = " + copyId + ") was borrowed successfully");
                    } else {
                        System.out.println("The book copy is already borrowed.");
                    }
                    return;
                }
            }
            System.out.println("The book copy (id = " + copyId + ") does not exist.");
        } else if (!customerExistsTests(userId)) {
            System.out.println("No customer with (id = " + userId + ") exists.");
        } else {
            System.out.println("The customer already has more than five books borrowed.");
        }
    }

    /**
     * Returns a borrowed book copy for a customer, and handles overdue fines if applicable.
     *
     * @param copyId the ID of the book copy
     * @param userId the ID of the customer
     */
    public void returnBookCopy(int copyId, int userId) {
        boolean temp = false;
        Customer customer = customerExists(userId);
        if (customer != null) {
            for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                if (bookCopy.getCopyId() == copyId) {
                    temp = true;
                    if (bookCopy.getUserId() == userId) {
                        if (LocalDate.now().isBefore(bookCopy.getBorrowedDate().plusDays(30))) {
                            bookCopy.setBorrowed(false);
                            bookCopy.setUserId(-1);
                            System.out.println("Book copy (id = " + copyId + ") was returned successfully");
                        } else {
                            bookCopy.setBorrowed(false);
                            bookCopy.setUserId(-1);
                            System.out.println("The maximum borrowing time is exceeded. An overdue fine will be charged");
                            System.out.println("Did the customer pay?");
                            System.out.println("Type yes/no");
                            Prompter prompter = new Prompter();
                            String answer = "";
                            if (prompter.hasnextLine()) {
                                answer = prompter.nextInput();
                            }
                            if (answer.equals("yes")) {
                                System.out.println("The payment has been successful. Book copy returned successfully");
                                Objects.requireNonNull(customerExists(userId)).setPaymentStatus(1);
                            } else {
                                System.out.println("The customer hasn't paid yet, the return is only possible after the payment of the fee");
                                Objects.requireNonNull(customerExists(userId)).setPaymentStatus(2);
                            }
                        }
                        return;
                    }
                }
            }
            if (temp) {
                System.out.println("The book copy (id = " + copyId + ") is not borrowed by that customer.");
            } else {
                System.out.println("The book copy (id = " + copyId + ") does not exist.");
            }
        } else {
            System.out.println("No customer with (id = " + userId + ") exists.");
        }
    }

    // DELETION OF CERTAIN BOOKS AND BOOKS COPIES

    /**
     * Deletes a book copy if it is not currently borrowed.
     *
     * @param copyId the ID of the book copy
     * @return true if the book copy was deleted, otherwise false
     */
    public boolean deleteBookCopy(int copyId) {
        boolean temp = false;
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.getCopyId() == copyId) {
                temp = true;
            }
            if (bookCopy.getCopyId() == copyId && !bookCopy.isBorrowed()) {
                BookCopy.getBookCopies().remove(bookCopy);
                System.out.println("Book copy (id = " + copyId + ") was deleted successfully");
                return true;
            }
        }
        if (temp) {
            System.out.println("That book copy is borrowed and cant be deleted.");
        } else {
            System.out.println("That book copy doesnt exist.");
        }
        return false;
    }

    /**
     * Deletes a book if it has no borrowed copies.
     *
     * @param ISBN the ISBN of the book
     * @return true if the book was deleted, otherwise false
     */
    public boolean deleteBook(String ISBN) {
        for (Book book : Book.getBooks()) {
            if (Objects.equals(book.getIsbn(), ISBN)) {
                for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                    if (Objects.equals(bookCopy.getIsbn(), ISBN)) {
                        if (!bookCopy.isBorrowed()) {
                            Book.getBooks().remove(book);
                            System.out.println("The Book with (ISBN = " + book.getIsbn() + ") was deleted successfully");
                            return true;
                        } else {
                            System.out.println("The book can not be deleted because it has borrowed book copies.");
                            return false;
                        }
                    }
                }
                Book.getBooks().remove(book);
                System.out.println("The book with (ISBN = " + book.getIsbn() + ") was deleted successfully");
                return true;
            }
        }
        System.out.println("No books with (ISBN = " + ISBN + ") were found.");
        return false;
    }

    // CUSTOMER

    /**
     * Deletes a customer if they have no borrowed books.
     *
     * @param userId the ID of the customer
     */
    public void deleteCustomer(int userId) {
        if (amountOfBooksPerCustomer(userId) > 0) {
            System.out.println("The customer can not be deleted because he has books borrowed.");
            return;
        }
        for (Customer customer : Customer.getCustomers()) {
            if (customer.getUserId() == userId && amountOfBooksPerCustomer(userId) == 0) {
                Customer.getCustomers().remove(customer);
                System.out.println("The customer with (id = " + userId + ") was removed successfully.");
                return;
            }
        }
        System.out.println("The customer with (id = " + userId + ") does not exist.");
    }

    /**
     * Creates a new customer if they do not already exist.
     *
     * @param firstname   the first name of the customer
     * @param name        the last name of the customer
     * @param mail        the email of the customer
     * @param phoneNumber the phone number of the customer
     */
    public void createCustomer(String firstname, String name, String mail, String phoneNumber) {
        for (Customer customer : Customer.getCustomers()) {
            if (customer.getMail().equals(mail)) {
                System.out.println("Customer with (mail = " + customer.getMail() + ") already exists.");
                return;
            }
        }
        Customer newCustomer = new Customer(firstname, name, mail, phoneNumber);
        System.out.println("Customer with id=" + newCustomer.getUserId() + " was added successfully.");
    }

    // SEARCH OPTIONS

    /**
     * Searches for books by their title.
     *
     * @param title the title of the book
     */
    public void searchByTitle(String title) {
        for (Book book : Book.getBooks()) {
            if (book.getTitle().equals(title)) {
                for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                    if (bookCopy.getIsbn().equals(book.getIsbn())) {
                        System.out.println("[" + book.getTitle() + ", " + book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + getBorrowingDateStatus(bookCopy) + "]");
                    }
                }
            }
        }
    }

    /**
     * Searches for books by their author.
     *
     * @param author the author of the book
     */
    public void searchByAuthor(String author) {
        for (Book book : Book.getBooks()) {
            if (book.getAuthor().equals(author)) {
                for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                    if (bookCopy.getIsbn().equals(book.getIsbn())) {
                        System.out.println("[" + book.getTitle() + ", " + book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + bookCopy.getBorrowedDate() + "]");
                    }
                }
            }
        }
    }

    /**
     * Searches for books by their ISBN.
     *
     * @param isbn the ISBN of the book
     */
    public void searchByISBN(String isbn) {
        for (Book book : Book.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                    if (bookCopy.getIsbn().equals(book.getIsbn())) {
                        System.out.println("[" + book.getTitle() + ", " + book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + bookCopy.getBorrowedDate() + "]");
                    }
                }
            }
        }
    }

    /**
     * Returns the borrowing status of a book copy.
     *
     * @param bookCopy the book copy
     * @return a string indicating whether the book copy is available or borrowed and, if so, the borrowing date
     */
    public String getBorrowingDateStatus(BookCopy bookCopy) {
        if (bookCopy.isBorrowed()) {
            return "Borrowed, " + bookCopy.getBorrowedDate().toString();
        } else {
            return "Available";
        }
    }
}