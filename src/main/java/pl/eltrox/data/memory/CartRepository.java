package pl.eltrox.data.memory;

import com.google.common.collect.ImmutableList;
import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.repository.CartRepositoryInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CartRepository implements CartRepositoryInterface {
    private Map<Long, Cart> items = new HashMap<>();
    private Long maxId = 0L;

    public CartRepository() {
    }

    @Override
    public Cart get(Long id) {
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
        }

        if (maxId < cart.getId()) {
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
