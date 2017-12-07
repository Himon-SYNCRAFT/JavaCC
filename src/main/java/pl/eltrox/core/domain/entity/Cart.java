package pl.eltrox.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Entity {

    private final Customer customer;
    private List<Product> products = new ArrayList<>();

    public Cart(Customer customer, Product product, Long id) {
        this(customer);
        this.id = id;
        this.products.add(product);
    }

    public Cart(Customer customer, Product product) {
        this(customer);
        this.products.add(product);
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Cart clone() {
        Cart cart = new Cart(customer.clone());
        cart.setId(this.id);

        for (Product product : products) {
            cart.addProduct(product.clone());
        }

        return cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
