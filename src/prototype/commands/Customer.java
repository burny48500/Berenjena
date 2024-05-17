package prototype.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Customer {
    private int userId;
    private String mail;
    private String phoneNumber;
    public static ArrayList<Customer> customers = new ArrayList<>();

    public Customer(String mail, String phoneNumber) {
        this.userId += 1;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }
    public static void creationCustomers(){
        Customer miguel = new Customer("miguel.cid@tum.de", "0034640882288");
        customers.add(miguel);
        Customer urko = new Customer("urko.cornejo@tum.de", "0034640932256");
        customers.add(urko);
    }

    public static void modifyMail(int userid, String newMail){

    }
    public static void modifyPhoneNumber(int userid, String newPhoneNumber){

    }

    public static void create(String name,String mail, String phoneNumber){
        // Create an userId so that its unique
    }

    public static void delete(int userId){
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.userId == userId) {
                for (Map.Entry<Integer, Integer> entry : BookCopy.copyBorrowers.entrySet()) {
                    if (entry.getValue() == userId) {
                        System.out.println("Cannot delete customer because they have borrowed books.");
                        return;
                    }
                }
                iterator.remove();
                System.out.println("Customer removed successfully.");
                return;
            }
        }
        System.out.println("Customer with that ID was not found.");
    }

    public static void importAccount(String mail, String phoneNumber){
        customers.add(new Customer(mail, phoneNumber));
        System.out.println("Customer imported successfully.");
    }
}