package prototype.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a customer in a library system.
 * Each customer has a unique user ID, name, contact information, and payment status.
 */
public class Customer {
    private static int nextId = 1;
    private int userId;
    private String mail;
    private String phoneNumber;
    private String name;
    private String firstName;
    private int paymentStatus;
    public static ArrayList<Customer> customers = new ArrayList<>();
    private static boolean text = false;

    /**
     * Constructs a new Customer.
     *
     * @param name        the surname of the customer
     * @param firstName   the first name of the customer
     * @param mail        the email address of the customer
     * @param phoneNumber the phone number of the customer
     */
    public Customer(String name, String firstName, String mail, String phoneNumber) {
        this.name = name;
        this.firstName = firstName;
        this.userId = nextId++;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        if (!sameCustomer(mail)) {
            customers.add(this);
            if (text) {
                System.out.println("The customer was added successfully!");
            }
        }
        this.paymentStatus = 0;
    }

    /**
     * Modifies the email of a customer.
     *
     * @param userid  the user ID of the customer
     * @param newMail the new email address
     */
    public static void modifyMail(int userid, String newMail) {
        boolean temp = false;
        for (Customer customer : customers) {
            if (customer.userId == userid) {
                customer.setMail(newMail);
                temp = true;
            }
        }
        if (temp){
            System.out.println("Mail of the customer changed successfully.");
        } else {
            System.out.println("The userId doesnt exist.");
        }
    }

    /**
     * Modifies the phone number of a customer.
     *
     * @param userid         the user ID of the customer
     * @param newPhoneNumber the new phone number
     */

    public static void modifyPhoneNumber(int userid, String newPhoneNumber) {
        boolean temp = false;
        for (Customer customer : customers) {
            if (customer.userId == userid) {
                customer.setPhoneNumber(newPhoneNumber);
                temp = true;
            }
        }
        if (temp){
            System.out.println("Mail of the customer changed successfully.");
        } else {
            System.out.println("The userId doesnt exist.");
        }
    }

    /**
     * Checks if a customer with the same email already exists.
     *
     * @param mail the email address to check
     * @return true if a customer with the same email exists, false otherwise
     */
    public static boolean sameCustomer(String mail) {
        for (Customer customer : customers) {
            if (customer.getMail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

    //GETTERS AND SETTERS
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        Customer.customers = customers;
    }

    public static boolean isText() {
        return text;
    }

    public static void setText(boolean text) {
        Customer.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Customer.nextId = nextId;
    }
}