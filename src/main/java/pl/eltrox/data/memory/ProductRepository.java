package pl.eltrox.data.memory;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;

import java.util.ArrayList;
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

    @Override
    public Collection<Product> find(Map filter) {
        Boolean shouldFilterByName = filter.get("name") != null;
        Boolean shouldFilterBySku = filter.get("sku") != null;
        Boolean shouldFilterById = filter.get("id") != null;

        ArrayList<Product> products = new ArrayList<>();

        for (Product product : items.values()) {
            Boolean shouldAddProduct = true;

            if (shouldFilterById && product.getId() != filter.get("id")) {
                shouldAddProduct = false;
            }

            if (shouldFilterByName) {
                String filteredName = (String) filter.get("name");

                if (!product.getName().toLowerCase().contains(filteredName.toLowerCase()))
                    shouldAddProduct = false;
            }

            if (shouldFilterBySku) {
                String filteredSku = (String) filter.get("sku");

                if (!product.getSku().toLowerCase().contains(filteredSku.toLowerCase()))
                    shouldAddProduct = false;
            }

            if(shouldAddProduct) {
                products.add(product);
            }
        }

        return products;
    }
}
