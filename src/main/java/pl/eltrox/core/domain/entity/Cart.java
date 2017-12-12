package pl.eltrox.core.domain.entity;

import java.util.*;

public class Cart extends Entity {

    private final Customer customer;
    private Collection<CartItem> items = new ArrayList<>();

    public Cart(Customer customer, Product product, Long id) {
        this(customer, product);
        this.id = id;
    }

    public Cart(Customer customer, Product product) {
        this(customer);
        this.addProduct(product);
    }

    public Collection<CartItem> getItems() {
        return items;
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            return;
        }

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.addQuantity(quantity);
                return;
            }
        }

        this.items.add(new CartItem(product, quantity));
    }

    public void addProduct(Product product) {
        this.addProduct(product, 1);
    }

    public boolean isProductInCart(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                return true;
            }
        }

        return false;
    }

    public int getProductQuantity(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item.getQuantity();
            }
        }

        return 0;
    }

    @Override
    public Cart clone() {
        Customer newCustomer = customer != null ? customer.clone() : null;
        Cart cart = new Cart(newCustomer);
        cart.setId(this.id);

        for (CartItem item : items) {
            cart.addProduct(item.getProduct().clone(), item.getQuantity());
        }

        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;

        return Objects.equals(customer, cart.customer) &&
                Objects.equals(items, cart.items) &&
                Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, items, id);
    }
}
