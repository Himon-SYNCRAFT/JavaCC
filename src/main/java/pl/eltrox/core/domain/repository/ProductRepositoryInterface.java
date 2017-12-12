package pl.eltrox.core.domain.repository;

import pl.eltrox.core.domain.entity.Product;

import java.util.Collection;
import java.util.Map;

public interface ProductRepositoryInterface extends RepositoryInterface<Product> {
    public Collection<Product> find(Map filter);
}
