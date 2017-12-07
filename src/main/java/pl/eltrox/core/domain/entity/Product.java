package pl.eltrox.core.domain.entity;

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
