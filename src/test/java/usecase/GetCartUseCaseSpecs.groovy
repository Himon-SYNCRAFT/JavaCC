package usecase

import pl.eltrox.data.memory.CartRepository
import pl.eltrox.data.memory.CustomerRepository
import pl.eltrox.data.memory.ProductRepository
import pl.eltrox.core.domain.entity.Cart
import pl.eltrox.core.domain.usecase.getcart.GetCartRequest
import pl.eltrox.core.domain.usecase.getcart.GetCartUseCase
import spock.lang.Specification

class GetCartUseCaseSpecs extends Specification {
    private CartRepository cartRepository;

    def setup() {
        cartRepository = new CartRepository()
        CustomerRepository customerRepository = new CustomerRepository()
        ProductRepository productRepository = new ProductRepository()

        Cart cart = new Cart(customerRepository.get(2L))
        cart.addProduct(productRepository.get(1L))
        cart.addProduct(productRepository.get(2L))
        cartRepository.save(cart)

        cart = new Cart(customerRepository.get(1L))
        cart.addProduct(productRepository.get(3L))
        cart.addProduct(productRepository.get(4L))
        cartRepository.save(cart)
    }

    def "GetCartUseCase.execute() return response with cart if given existing id"() {
        setup:
        def cartId = 1L
        def useCase = new GetCartUseCase(cartRepository)
        def request = new GetCartRequest(cartId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData().id == cartId
        response.hasErrors() == false
        response.getErrors().size() == 0
    }

    def "GetCartUseCase.execute() return response with error if cart cannot be find"() {
        setup:
        def cartId = 9L
        def useCase = new GetCartUseCase(cartRepository)
        def request = new GetCartRequest(cartId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }
}
