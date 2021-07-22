package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private static Map<String, Customer> mapOfCustomers = new HashMap<String, Customer>();

    public CustomerService() {
        mapOfCustomers = new HashMap<String, Customer>();
    }

    public static void addCustomer(String email, String firstName, String lastName) {
        mapOfCustomers.put(email, new Customer(firstName, lastName, email));
    }

    public static Customer getCustomer(String customerEmail) {
        return mapOfCustomers.getOrDefault(customerEmail, null);
    }

    public static Collection<Customer> getAllCustomers() {
        return mapOfCustomers.values();
    }
}
