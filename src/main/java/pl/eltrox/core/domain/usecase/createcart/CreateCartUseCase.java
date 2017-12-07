package pl.eltrox.core.domain.usecase.createcart;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.CartRepositoryInterface;
import pl.eltrox.core.domain.repository.CustomerRepositoryInterface;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

public class CreateCartUseCase implements UseCaseInterface<CreateCartResponse, CreateCartRequest> {
    private final ProductRepositoryInterface productRepository;
    private final CustomerRepositoryInterface customerRepository;
    private final CartRepositoryInterface cartRepository;

    public CreateCartUseCase(CartRepositoryInterface cartRepository, ProductRepositoryInterface productRepository,
                             CustomerRepositoryInterface userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = userRepository;
    }

    @Override
    public CreateCartResponse execute(CreateCartRequest request) {
        Customer customer = customerRepository.get(request.getCustomerId());
        Product product = null;

        if (request.getProductId() != null) {
           product = productRepository.get(request.getProductId());
        }

        Cart cart = new Cart(customer);
        cart.addProduct(product);
        cart = cartRepository.save(cart);

        return new CreateCartResponse(cart);
    }
}
