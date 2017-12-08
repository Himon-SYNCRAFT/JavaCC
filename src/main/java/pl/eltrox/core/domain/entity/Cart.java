package pl.eltrox.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart extends Entity {

    private final Customer customer;
    private List<Product> products = new ArrayList<>();

    public Cart(Customer customer, Product product, Long id) {
        this(customer, product);
        this.id = id;
    }

    public Cart(Customer customer, Product product) {
        this(customer);
        this.addProduct(product);
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public boolean isProductInCart(Product product) {
        return products.contains(product);
    }

    @Override
    public Cart clone() {
        Customer newCustomer = customer != null ? customer.clone() : null;
        Cart cart = new Cart(newCustomer);
        cart.setId(this.id);

        for (Product product : products) {
            cart.addProduct(product.clone());
        }

        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;

        return Objects.equals(customer, cart.customer) &&
                Objects.equals(products, cart.products) &&
                Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, products, id);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        this.products.add(product);
    }
}
