package usecase

import pl.eltrox.core.domain.entity.Cart
import pl.eltrox.core.domain.entity.Customer
import pl.eltrox.core.domain.entity.Product
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartRequest
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartUseCase
import pl.eltrox.data.fakeverto.ProductRepository
import pl.eltrox.data.memory.CartRepository
import pl.eltrox.data.memory.CustomerRepository
import spock.lang.Specification

class AddProductToCartUseCaseSpecs extends Specification {
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    def setup() {
        customerRepository = new CustomerRepository()
        customerRepository.save(new Customer("firstName", "lastName", 1L))

        productRepository = new ProductRepository()
        productRepository.save(new Product("name", "sku", 1L))

        cartRepository = new CartRepository()
        Cart cart = new Cart(customerRepository.get(1L))
        cart.addProduct(productRepository.get(1L))
        cart.addProduct(productRepository.get(2L))
        cartRepository.save(cart)
    }

    def "AddProductToCart.execute() should add product to cart if given existing cartId and productId"() {
        setup:
        def cartId = 1L
        def productId = 1L
        def cart = cartRepository.get(cartId)
        def product = productRepository.get(productId)
        def countProduct = cart.getProductQuantity(product)

        def useCase = new AddProductToCartUseCase(cartRepository, productRepository)

        when:
        def request = new AddProductToCartRequest(cartId, productId)
        def response = useCase.execute(request)

        then:
        !response.hasErrors()
        response.getErrors().size() == 0
        response.getData().getProductQuantity(product) == countProduct + 1
    }

    def "AddProductToCart.execute() should return response with error if given not existing cartId"() {
        setup:
        def cartId = 9L
        def productId = 1L

        def useCase = new AddProductToCartUseCase(cartRepository, productRepository)

        when:
        def request = new AddProductToCartRequest(cartId, productId)
        def response = useCase.execute(request)

        then:
        response.hasErrors()
        response.getErrors().size() != 0
    }

    def "AddProductToCart.execute() should return response with error if given not existing productId"() {
        setup:
        def cartId = 1L
        def productId = 9L

        def useCase = new AddProductToCartUseCase(cartRepository, productRepository)

        when:
        def request = new AddProductToCartRequest(cartId, productId)
        def response = useCase.execute(request)

        then:
        response.hasErrors()
        response.getErrors().size() != 0
    }
}
