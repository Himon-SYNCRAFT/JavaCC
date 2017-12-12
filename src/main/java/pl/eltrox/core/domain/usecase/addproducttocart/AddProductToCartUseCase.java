package pl.eltrox.core.domain.usecase.addproducttocart;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.CartRepositoryInterface;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

public class AddProductToCartUseCase implements UseCaseInterface<AddProductToCartResponse, AddProductToCartRequest> {
    private final CartRepositoryInterface cartRepository;
    private final ProductRepositoryInterface productRepository;

    public AddProductToCartUseCase(CartRepositoryInterface cartRepository, ProductRepositoryInterface productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public AddProductToCartResponse execute(AddProductToCartRequest request) {
        AddProductToCartResponse response = new AddProductToCartResponse();

        Long cartId = request.getCartId();
        Long productId = request.getProductId();

        Cart cart = cartRepository.get(cartId);

        if (cart == null) {
            response.addError("Invalid cartId. Cart not found.");
            return response;
        }

        Product product = productRepository.get(productId);

        if (product == null) {
            response.addError("Invalid productId. Product not found.");
            return response;
        }

        cart.addProduct(product);
        cart = cartRepository.save(cart);

        response.setData(cart);

        return response;
    }
}
