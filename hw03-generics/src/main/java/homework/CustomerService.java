package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> treeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        if (treeMap.isEmpty()) {
            return null;
        }
        Map.Entry<Customer, String> customerStringEntry = treeMap.firstEntry();
        Customer customer = customerStringEntry.getKey();
        if (customer == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(
                new Customer(customer.getId(), customer.getName(), customer.getScores()),
                customerStringEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerStringEntry = treeMap.higherEntry(customer);
        if (customerStringEntry == null) {
            return null;
        }
        final Customer key = customerStringEntry.getKey();
        return new AbstractMap.SimpleEntry<>(
                new Customer(key.getId(), key.getName(), key.getScores()), customerStringEntry.getValue());
    }

    public void add(Customer customer, String data) {
        treeMap.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
