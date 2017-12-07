package pl.eltrox.apps.cli.data;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository implements ProductRepositoryInterface {
    private Map<Long, Product> items = new HashMap<>();
    private Long maxId;

    public ProductRepository() {
        items.put(1L, new Product("Kamera", "1", 1L));
        items.put(2L, new Product("Rejestrator", "2", 2L));
        items.put(3L, new Product("Dysk", "3", 3L));
        items.put(4L, new Product("Przewód", "4", 4L));

        maxId = 4L;
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
        } else if (maxId < product.getId()) {
            maxId = product.getId();
        }

        items.put(product.getId(), product);
        return product;
    }

    @Override
    public Product get(long id) {
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
