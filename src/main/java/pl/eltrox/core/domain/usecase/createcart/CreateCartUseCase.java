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
                             CustomerRepositoryInterface customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CreateCartResponse execute(CreateCartRequest request) {
        CreateCartResponse response = new CreateCartResponse();
        Long customerId = request.getCustomerId();

        if (customerId == null) {
            response.addError("Cannot create cart without customer");
            return response;
        }

        Customer customer = customerRepository.get(request.getCustomerId());

        if (customer == null) {
            response.addError("Provided customerId is invalid. Customer not found");
            return response;
        }

        Product product = null;
        Cart cart = new Cart(customer);

        if (request.getProductId() != null) {
            product = productRepository.get(request.getProductId());

            if (product == null) {
                response.addError("Provided productId is invalid. Product not found");
                return response;
            }

            cart.addProduct(product);
        }

        cart = cartRepository.save(cart);
        response.setData(cart);
        return response;
    }
}
