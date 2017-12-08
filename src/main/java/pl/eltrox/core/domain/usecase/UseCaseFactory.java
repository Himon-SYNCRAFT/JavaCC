package pl.eltrox.core.domain.usecase;

import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.usecase.getcart.GetCartUseCase;
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerUseCase;
import pl.eltrox.core.domain.usecase.getproduct.GetProductUseCase;
import pl.eltrox.data.memory.CartRepository;
import pl.eltrox.data.memory.CustomerRepository;
import pl.eltrox.data.memory.ProductRepository;

public class UseCaseFactory {
    public static GetCartUseCase GetCartUseCase() {
        CartRepository cartRepository = new CartRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();

        Cart cart = new Cart(customerRepository.get(2L));
        cart.addProduct(productRepository.get(1L));
        cart.addProduct(productRepository.get(2L));
        cartRepository.save(cart);

        GetCartUseCase useCase = new GetCartUseCase(cartRepository);

        return useCase;
    }

    public static GetProductUseCase GetProductUseCase() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.save(new Product("name", "sku", 1L));

        GetProductUseCase useCase = new GetProductUseCase(productRepository);
        return useCase;
    }

    public static GetCustomerUseCase GetCustomerUseCase() {
        CustomerRepository customerRepository =  new CustomerRepository();
        customerRepository.save(new Customer("firstName", "lastName", 1L));

        GetCustomerUseCase useCase = new GetCustomerUseCase(customerRepository);
        return useCase;
    }
}
