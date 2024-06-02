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
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (bookCopy.isBorrowed()) {
                Book book = Book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: "+book.getTitle()+"; Author: "+book.getAuthor()+
                        "; Year: "+book.getYear()+"; ISBN: "+book.getIsbn()+ "; Copy ID: "+bookCopy.getCopyId());
            }

        }
    }
    public static void allNonBorrowedCopies(){
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {
            if (!bookCopy.isBorrowed()) {
                Book book = Book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: "+book.getTitle()+"; Author: "+book.getAuthor()+
                        "; Year: "+book.getYear()+"; ISBN: "+book.getIsbn()+ "; Copy ID: "+bookCopy.getCopyId());
            }

        }
    }
    public static void allCustomers(){
        for (Customer customer : Customer.getCustomers()) {
            String status;
            int userId = customer.getUserId();
            int booksBorrowed=0;

            if (customer.isPaymentStatus()) {
                status = "Paid";
            } else {
                status = "Not Paid";
            }

            for (BookCopy bookCopy : BookCopy.getBookCopies()) {
                if (userId == bookCopy.getUserId() && bookCopy.isBorrowed()) {
                    booksBorrowed++;
                }
            }
            System.out.println("User ID: " + customer.getUserId() + "; First Name: " + customer.getFirstName() +
                    "; Name: " + customer.getName() + "; Payment Status: " +status+"; Number of Books Currently Borrowed: "
                    + booksBorrowed);

        }

    }
    public static void BorrowedCustomer(int userId){
        for (BookCopy bookCopy : BookCopy.getBookCopies()) {

            if (userId == bookCopy.getUserId()) {
                Book book = Book.getBookByIsbn(bookCopy.getIsbn());
                assert book != null;
                System.out.println("Title: " + book.getTitle() + "; Author: " + book.getAuthor() +
                        "; Year: " + book.getYear() + "; ISBN: " + book.getIsbn() + "; Copy ID: " + bookCopy.getCopyId());
            }
        }
    }

}