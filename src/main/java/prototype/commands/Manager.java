package prototype.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Manager {
    // CREATION OF BOOKS;BOOK COPIES;CUSTOMERS
    public static void creationBooks(){
        new Book("Berenjena", "Dr Pepper","0-7642-1858-1", "1980");
        new Book("Tomatoes", "Ibañez","0-7050-3533-6", "2005");
    }
    public static void creationBookCopies(){
        new BookCopy("0-7642-1858-1", "A2");
        new BookCopy("0-7642-1858-1", "B3");
        new BookCopy("0-7050-3533-6", "C7");
    }
    public static void creationCustomers() {
        new Customer("Cid", "Miguel", "miguel.cid@tum.de", "0034640882288");
        new Customer("Cornejo", "Urko", "urko.cornejo@tum.de", "0034640932256");
        Customer.setText(true);
    }

    private static int amountOfBooksPerCustomer(int userId){
        int count = 0;
        for (BookCopy bookCopy : BookCopy.bookCopies){
            if (bookCopy.getUserId() == userId){
                count+=1;
            }
        }
        return count;
    }

    public static Customer customerExists(int userId){
        for (Customer customer :  Customer.customers){
            if (customer.getUserId() == userId){
                return customer;
            }
        }
        return null;
    }
    public static boolean customerExistsTests(int userId){
        for (Customer customer :  Customer.customers){
            if (customer.getUserId() == userId){
                return true;
            }
        }
        return false;
    }
    // BORROW AND RETURN OF A BOOK COPY
    public static void borrowBook(int copyId, int userId){
        if (amountOfBooksPerCustomer(userId) < 5 && customerExistsTests(userId)){
            for (BookCopy bookCopy : BookCopy.bookCopies){
                if (bookCopy.getCopyId() == copyId){
                    if (!bookCopy.isBorrowed()){
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
    public static void returnBook(int copyId, int userId){
        boolean temp = false;
        if (customerExists(userId)!=null){
            for (BookCopy bookCopy : BookCopy.bookCopies){
                if (bookCopy.getCopyId() == copyId) {
                    temp = true;
                    if (bookCopy.getUserId() == userId) {
                        if (bookCopy.getBorrowedDate().isBefore(bookCopy.getBorrowedDate().plusDays(30))) {
                            bookCopy.setBorrowed(false);
                            bookCopy.setUserId(-1);
                            System.out.println("Book copy (id = " + copyId + ") was returned successfully");
                            Objects.requireNonNull(customerExists(userId)).setPaymentStatus(true);
                        } else {
                            bookCopy.setBorrowed(false);
                            bookCopy.setUserId(-1);
                            Objects.requireNonNull(customerExists(userId)).setPaymentStatus(true);
                            System.out.println("The maximum borrowing time is exceeded. Book returned successfully");
                        }
                        return;
                    }
                    temp = false;
                }
            }
            if (temp){
                System.out.println("The book copy (id = " + copyId + ") is not borrowed by that customer.");
            } else {
                System.out.println("The book copy (id = " + copyId + ") does not exist.");
            }
        } else {
            System.out.println("No customer with (id = " + userId + ") exists.");
        }
    }
    // DELETION OF BOOKS AND BOOKS COPIES
    public static boolean deleteBookCopy(int copyId){
        boolean temp = false;
        for (BookCopy bookCopy : BookCopy.bookCopies){
            if (bookCopy.getCopyId() == copyId){
                temp = true;
            }
            if (bookCopy.getCopyId() == copyId && !bookCopy.isBorrowed()){
                bookCopy.getBookCopies().remove(copyId);
                System.out.println("Book copy (id = " + copyId + ") was deleted successfully");
                return true;
            }
        }
        if (temp){
            System.out.println("That book copy is borrowed and cant be deleted.");
        }else {
            System.out.println("That book copy doesnt exist.");
        }
        return false;
    }
    public static boolean deleteBook(String ISBN){
        for (Book book : Book.getBooks()){
            if (Objects.equals(book.getIsbn(), ISBN)){
                for (BookCopy bookCopy : BookCopy.bookCopies){
                    if (Objects.equals(bookCopy.getIsbn(), ISBN)){
                        if (!bookCopy.isBorrowed()){
                            Book.getBooks().remove(book);
                            System.out.println("The Book with (ISBN = " + book.getIsbn() + ") was deleted successfully");
                            return true;
                        } else {
                            System.out.println("The book can not be deleted cause it has borrowed book copies.");
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

    // CUSTOMERS
    public static boolean deleteCustomer(int userId){
        if (amountOfBooksPerCustomer(userId)>0){
            System.out.println("The customer can not be deleted because he has books borrowed.");
            return false;
        }
        for (Customer customer : Customer.getCustomers()){
            if (customer.getUserId() == userId && amountOfBooksPerCustomer(userId)==0){
                Customer.getCustomers().remove(customer);
                System.out.println("The customer with (id = " + userId + ") was removed successfully.");
                return true;
            }
        }
        System.out.println("The customer with (id = " + userId + ") does not exist.");
        return false;
    }

    public static boolean createCustomer(String firstname, String name, String mail, String phoneNumber){
        for (Customer customer : Customer.getCustomers()){
            if (customer.getMail().equals(mail)){
                System.out.println("Customer with (mail = " + customer.getMail() + ") already exists.");
                return false;
            }
        }
        Customer newCustomer = new Customer(firstname, name, mail, phoneNumber);
        System.out.println("Customer with id="+ newCustomer.getUserId()+" was added successfully.");
        return true;
    }

    // SEARCH OPTIONS
    public static void searchByTitle(String title) {
        for (Book book : Book.getBooks()){
            if (book.getTitle().equals(title)) {
                for (BookCopy bookCopy : BookCopy.bookCopies){
                    if (bookCopy.getIsbn().equals(book.getIsbn())){
                        System.out.println("[" + book.getTitle() + ", "+ book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + getBorrowingDateStatus(bookCopy) + "]");
                    }
                }
            }
        }
    }
    public static String getBorrowingDateStatus(BookCopy bookCopy){
        if (bookCopy.isBorrowed()){
            return "Borrowed, "+ bookCopy.getBorrowedDate().toString();
        }else {
            return "Available";
        }
    }
    public static void searchByAuthor(String author) {
        for (Book book : Book.getBooks()){
            if (book.getAuthor().equals(author)) {
                for (BookCopy bookCopy : BookCopy.bookCopies){
                    if (bookCopy.getIsbn().equals(book.getIsbn())){
                        System.out.println("[" + book.getTitle() + ", "+ book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + bookCopy.getBorrowedDate() + "]");
                    }
                }
            }
        }
    }
    public static void searchByISBN(String isbn) {
        for (Book book : Book.getBooks()){
            if (book.getIsbn().equals(isbn)) {
                for (BookCopy bookCopy : BookCopy.bookCopies){
                    if (bookCopy.getIsbn().equals(book.getIsbn())){
                        System.out.println("[" + book.getTitle() + ", "+ book.getAuthor() + ", " + book.getIsbn() + ", "
                                + bookCopy.getCopyId() + ", " + bookCopy.getShelfLocation() + ", "
                                + bookCopy.getBorrowedDate() + "]");
                    }
                }
            }
        }
    }
}
