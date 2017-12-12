package pl.eltrox.core.domain.usecase;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartUseCase;
import pl.eltrox.core.domain.usecase.createcart.CreateCartUseCase;
import pl.eltrox.core.domain.usecase.findproduct.FindProductUseCase;
import pl.eltrox.core.domain.usecase.getcart.GetCartUseCase;
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerUseCase;
import pl.eltrox.core.domain.usecase.getproduct.GetProductUseCase;
import pl.eltrox.data.memory.CartRepository;
import pl.eltrox.data.memory.CustomerRepository;
import pl.eltrox.data.fakeverto.ProductRepository;

public class UseCaseFactory {
    private final static CustomerRepository customerRepository = new CustomerRepository();
    private final static ProductRepository productRepository = new ProductRepository();
    private final static CartRepository cartRepository = new CartRepository();

    static {
        customerRepository.save(new Customer("firstName", "lastName", 1L));
        productRepository.save(new Product("name", "sku", 1L));

        Cart cart = new Cart(customerRepository.get(1L));
        cart.addProduct(productRepository.get(1L));
        cart.addProduct(productRepository.get(2L));

        cartRepository.save(cart);
    }

    public static GetCartUseCase GetCartUseCase() {
        return new GetCartUseCase(cartRepository);
    }

    public static GetProductUseCase GetProductUseCase() {
        return new GetProductUseCase(productRepository);
    }

    public static GetCustomerUseCase GetCustomerUseCase() {
        return new GetCustomerUseCase(customerRepository);
    }

    public static FindProductUseCase FindProductUseCase() {
        return new FindProductUseCase(productRepository);
    }

    public static CreateCartUseCase CreateCartUseCase() {
        return new CreateCartUseCase(cartRepository, productRepository, customerRepository);
    }

    public static AddProductToCartUseCase AddProductToCartUseCase() {
        return new AddProductToCartUseCase(cartRepository, productRepository);
    }
}
