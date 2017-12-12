package pl.eltrox.core.domain.usecase.createcart;

import pl.eltrox.core.domain.usecase.RequestInterface;

public class CreateCartRequest implements RequestInterface {
    private final Long customerId;
    private final Long productId;

    public CreateCartRequest(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public CreateCartRequest(Long customerId) {
        this.customerId = customerId;
        this.productId = null;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
