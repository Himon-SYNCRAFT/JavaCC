package pl.eltrox.data.memory;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.repository.CustomerRepositoryInterface;

import java.util.*;

public class CustomerRepository implements CustomerRepositoryInterface {
    private Map<Long, Customer> items = new HashMap<>();
    private Long maxId = 0L;

    public CustomerRepository() {
    }

    public CustomerRepository(Collection<Customer> items) {
        for (Customer customer : items) {
            save(customer);
        }
    }

    @Override
    public Customer get(Long id) {
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
        }

        if (maxId < customer.getId()) {
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
