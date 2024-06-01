package prototype.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Customer {
    private static int nextId = 1;
    private int userId;
    private String mail;
    private String phoneNumber;
    private String name;
    private String firstName;
    private boolean paymentStatus;
    public static ArrayList<Customer> customers = new ArrayList<>();
    private static boolean text = false;

    public Customer(String name, String firstName, String mail, String phoneNumber) {
        this.name = name;
        this.firstName = firstName;
        this.userId = nextId++;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        if (!sameCustomer(mail)) {
            customers.add(this);
            if(text){
                System.out.println("The customer was added successfully!");
            }
        }
        this.paymentStatus = false;
    }

    public static void modifyMail(int userid, String newMail) {

    }

    public static void modifyPhoneNumber(int userid, String newPhoneNumber) {

    }

    public static boolean sameCustomer(String mail) {
        for (Customer customer : customers) {
            if (customer.getMail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

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

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
