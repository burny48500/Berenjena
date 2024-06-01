package prototype.commands;

import java.util.Map;

public class Reports {
    public static void allBooks(){
        for (Book book : Book.getBooks()) {
            System.out.println("Title: "+book.getTitle()+"; Author: "+book.getAuthor()+
                    "; Year: "+book.getYear()+"; ISBN: "+book.getIsbn());
        }
    }
    public static void allBorrowedCopies(){
        for (Map.Entry<Integer, Integer> entry : BookCopy.getCopyBorrowers().entrySet()) {
            int copyId = entry.getKey();
            String isbn = BookCopy.copyIdToIsbn(copyId);
            Book book = Book.getBookByIsbn(isbn);
            System.out.println("Title: "+book.getTitle()+"; Author: "+book.getAuthor()+
                    "; Year: "+book.getYear()+"; ISBN: "+book.getIsbn()+ "; Copy ID: "+copyId);
        }
    }
    public static void allNonBorrowedCopies(){
        for (Map.Entry<Integer, Boolean> entry : BookCopy.getBorrowStatus().entrySet()) {
            int copyId = entry.getKey();
            boolean status = entry.getValue();
            if (!status) {
                String isbn = BookCopy.copyIdToIsbn(copyId);
                if (BookCopy.getCopyToBookMap().containsValue(isbn)) {
                    Book book = Book.getBookByIsbn(isbn);
                    System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                            "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + copyId);
                } else {
                    System.out.println("No book found!");
                }
            }
        }
    }
    public static void allCustomers(){
        for (Customer customer : Customer.getCustomers()) {
            String status;
            if (customer.isPaymentStatus()) {
                status = "Paid";
            } else {
                status = "Not Paid";
            }
            System.out.println("User ID: " + customer.getUserId() + "; First Name: " + customer.getFirstName() +
                    "; Name: " + customer.getName() + "; Payment Status: " +status+"; Number of Books Currently Borrowed: "
                    + BookCopy.amountOfBooksPerCustomer(BookCopy.getCopyBorrowers(), customer.getUserId()));
        }

    }
    public static void BorrowedCustomer(int userId){
        for (Map.Entry<Integer, Integer> entry : BookCopy.getCopyBorrowers().entrySet()) {
            int copyId = entry.getKey();
            int entryUserId = entry.getValue();

            if (userId == entryUserId) {
                String isbn = BookCopy.copyIdToIsbn(copyId);
                Book book = Book.getBookByIsbn(isbn);
                if (BookCopy.getCopyToBookMap().containsValue(isbn)) {
                    System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                            "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + copyId);
                } else {
                    System.out.println("No book found!");
                }
            }
        }
    }
}

