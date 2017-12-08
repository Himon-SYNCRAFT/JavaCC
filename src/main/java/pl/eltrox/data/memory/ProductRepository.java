package pl.eltrox.data.memory;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository implements ProductRepositoryInterface {
    private Map<Long, Product> items = new HashMap<>();
    private Long maxId = 0L;

    public ProductRepository() {
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            return null;
        }

        product = product.clone();

        if (product.getId() == null) {
            maxId++;
            product.setId(maxId);
        }

        if (maxId < product.getId()) {
            maxId = product.getId();
        }

        items.put(product.getId(), product);
        return product;
    }

    @Override
    public Product get(Long id) {
        Product product = items.get(id);

        if (product != null) {
            return product.clone();
        }

        return null;
    }

    @Override
    public Collection<Product> all() {
        return ImmutableList.copyOf(items.values());
    }

    @Override
    public int count() {
        return items.size();
    }
}
