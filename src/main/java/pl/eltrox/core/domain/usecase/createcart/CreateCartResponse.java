package pl.eltrox.core.domain.usecase.createcart;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.usecase.Response;

public class CreateCartResponse extends Response<Cart> {
    public CreateCartResponse(Cart cart) {
        super(cart);
    }
}
