package pl.eltrox.core.domain.usecase.createcart;

public class CreateCartRequest {
    private final Long customerId;
    private final Long productId;

    public CreateCartRequest(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
