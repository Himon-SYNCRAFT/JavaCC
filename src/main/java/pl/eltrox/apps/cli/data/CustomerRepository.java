package pl.eltrox.apps.cli.data;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.repository.CustomerRepositoryInterface;

import java.util.*;

public class CustomerRepository implements CustomerRepositoryInterface {
    private Map<Long, Customer> items = new HashMap<>();
    private Long maxId;

    public CustomerRepository() {
        items.put(1L, new Customer("John", "Doe", 1L));
        items.put(2L, new Customer("Jane", "Doe", 2L));
        items.put(3L, new Customer("Spike", "Spiegel", 3L));
        items.put(4L, new Customer("Geralt", "z Rivii", 4L));

        maxId = 4L;
    }

    public CustomerRepository(Collection<Customer> items) {
        maxId = 0L;

        for (Customer customer : items) {
            save(customer);
        }
    }

    @Override
    public Customer get(long id) {
        Customer customer = items.get(id);

        if (customer != null) {
            return customer.clone();
        }

        return null;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer == null) {
            return null;
        }

        customer = customer.clone();

        if (customer.getId() == null) {
            maxId++;
            customer.setId(maxId);
        } else if (maxId < customer.getId()) {
            maxId = customer.getId();
        }

        items.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Collection<Customer> all() {
        return ImmutableList.copyOf(items.values());
    }

    @Override
    public int count() {
        return items.size();
    }
}
