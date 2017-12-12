package pl.eltrox.core.domain.usecase.addproducttocart;

import pl.eltrox.core.domain.usecase.RequestInterface;

public class AddProductToCartRequest implements RequestInterface {
    private final Long cartId;
    private final Long productId;

    public AddProductToCartRequest(Long cartId, Long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
