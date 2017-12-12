package pl.eltrox.core.domain.repository;

import pl.eltrox.core.domain.entity.Customer;

import java.util.Collection;
import java.util.Map;

public interface CustomerRepositoryInterface extends RepositoryInterface<Customer> {
    public Collection<Customer> find(Map filter);
}
