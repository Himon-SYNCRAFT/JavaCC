package pl.eltrox.core.domain.entity;

import java.util.Objects;

public class Product extends Entity {
    private String name;
    private String sku;

    public Product(String name, String sku, Long id) {
        this(name, sku);
        this.id = id;
    }

    public Product(String name, String sku) {
        this.name = name;
        this.sku = sku;
    }

    @Override
    public Product clone() {
        return new Product(name, sku, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(name, product.name) &&
                Objects.equals(sku, product.sku) &&
                Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sku, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
