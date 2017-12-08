package pl.eltrox.core.domain.usecase.getcart;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.repository.CartRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

public class GetCartUseCase implements UseCaseInterface<GetCartResponse, GetCartRequest> {
    private final CartRepositoryInterface cartRepository;

    public GetCartUseCase(CartRepositoryInterface cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public GetCartResponse execute(GetCartRequest request) {
        Long cartId = request.getId();
        Cart cart = cartRepository.get(cartId);

        GetCartResponse response = new GetCartResponse();

        if (cart == null) {
            response.addError(String.format("Cart with id %s not found", cartId));
        } else {
            response.setData(cart);
        }

        return response;
    }
}
