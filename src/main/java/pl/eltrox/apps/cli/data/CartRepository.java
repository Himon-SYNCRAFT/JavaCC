package pl.eltrox.apps.cli.data;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.repository.CartRepositoryInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CartRepository implements CartRepositoryInterface {
    private Map<Long, Cart> items = new HashMap<>();
    private Long maxId;

    public CartRepository() {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();

        items.put(1L, new Cart(customerRepository.get(1L)));

        Cart cart = new Cart(customerRepository.get(2L));
        cart.addProduct(productRepository.get(1L));
        cart.addProduct(productRepository.get(2L));

        items.put(2L, cart);

        maxId = 2L;
    }

    @Override
    public Cart get(long id) {
        Cart cart = items.get(id);

        if (cart != null) {
            return cart;
        }

        return null;
    }

    @Override
    public Collection<Cart> all() {
        return ImmutableList.copyOf(items.values());
    }

    @Override
    public Cart save(Cart cart) {
        if (cart == null) {
            return null;
        }

        cart = cart.clone();

        if (cart.getId() == null) {
            maxId++;
            cart.setId(maxId);
        } else if (maxId < cart.getId()) {
            maxId = cart.getId();
        }

        items.put(cart.getId(), cart);
        return cart;
    }

    @Override
    public int count() {
        return items.size();
    }
}
