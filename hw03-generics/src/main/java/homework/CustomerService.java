package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    private final TreeMap<Customer, String> treeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> customerStringEntry = treeMap.firstEntry();
        Customer customer = customerStringEntry.getKey();
        return new AbstractMap.SimpleEntry<>(
                new Customer(customer.getId(), customer.getName(), customer.getScores()),
                customerStringEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        return treeMap.entrySet().stream()
                .filter(c -> c.getKey().getScores() >= customer.getScores())
                .min(Comparator.comparingLong(entry -> entry.getKey().getScores()))
                .<Map.Entry<Customer, String>>map(AbstractMap.SimpleEntry::new)
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        treeMap.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
