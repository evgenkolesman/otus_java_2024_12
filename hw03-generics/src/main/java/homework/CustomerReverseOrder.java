package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> list = new ArrayDeque<>();

    public void add(Customer customer) {
        list.add(new Customer(customer.getId(), customer.getName(), customer.getScores()));
    }

    public Customer take() {
        return list.pollLast();
    }
}
