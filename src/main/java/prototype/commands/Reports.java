package prototype.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Reports class provides functionality to generate and print various reports
 * related to books, book copies, and customers in the system.
 */
public class Reports {
    private Book book;
    private BookCopy bookCopy;
    private Customer customer;

    /**
     * Prints a report of all books in the system.
     */
    public void allBooks() {
        for (Book book : Book.getBooks()) {
            System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                    "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn());
        }
    }

    /**
     * Prints a report of all borrowed book copies.
     */
    public void allBorrowedCopies() {
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.isBorrowed()) {
                Book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                        "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + bookCopy.getCopyId());
            }

        }
    }

    /**
     * Prints a report of all non-borrowed book copies.
     */
    public void allNonBorrowedCopies() {
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (!bookCopy.isBorrowed()) {
                book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                        "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + bookCopy.getCopyId());
            }

        }
    }

    /**
     * Prints a report of all customers in the system.
     */
    public void allCustomers() {
        for (Customer customer : Customer.getCustomers()) {
            String status = "";
            int userId = customer.getUserId();
            int booksBorrowed = 0;

            switch (customer.getPaymentStatus()) {
                case 0:
                    status = "No fee";
                    break;
                case 1:
                    status = "Fee payed";
                    break;
                case 2:
                    status = "Fee not payed";
                    break;
            }

            for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                if (userId == bookCopy.getUserId() && bookCopy.isBorrowed()) {
                    booksBorrowed++;
                }
            }
            System.out.println("User ID: " + customer.getUserId() + "; First Name: " + customer.getFirstName() +
                    "; Name: " + customer.getName() + "; Payment Status: " + status + "; Number of Books Currently Borrowed: "
                    + booksBorrowed);
        }
    }

    /**
     * Prints a list of customers who have borrowed books.
     */
    public void BorrowedCustomer(int userId) {
        boolean customerExists = false;

        for (Customer customer : customer.getCustomers()) {
            if (customer.getUserId() == userId) {
                customerExists = true;
                break;
            }
        }
        if (!customerExists) {
            System.out.println("Customer with ID " + userId + " does not exist.");
            return;
        }
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {

            if (userId == bookCopy.getUserId()) {
                book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                        "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + bookCopy.getCopyId());
            }
        }
    }

    /**
     * Generates and prints a report of the number of book copies per publisher.
     */
    public void NumberOfBookCopiesPerPublisher() {
        ArrayList<String> listOfPublishers = new ArrayList<>();
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            listOfPublishers.add(bookCopy.getPublisher());
        }
        double numberOfBookCopies = listOfPublishers.size();

        if (numberOfBookCopies > 0) {
            Map<String, Long> counts = listOfPublishers.
                    stream().sorted().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            for (Map.Entry<String, Long> entry : counts.entrySet()) {
                String copiesText = "";
                if (entry.getValue() == 1) {
                    copiesText = " book copy (";
                } else if (entry.getValue() > 1) {
                    copiesText = " book copies (";
                }
                double finalNumber = (entry.getValue() * 100) / numberOfBookCopies;
                finalNumber = Math.floor(finalNumber * 100) / 100;
                String formatted = String.format(Locale.US, "%.1f", finalNumber);
                System.out.println(entry.getKey() + ": " + entry.getValue() + copiesText + formatted + "%)");
            }
        } else {
            System.out.println("No book copies found.");
        }
    }
}