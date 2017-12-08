package usecase

import pl.eltrox.data.memory.CartRepository
import pl.eltrox.data.memory.CustomerRepository
import pl.eltrox.data.memory.ProductRepository
import pl.eltrox.core.domain.entity.Cart
import pl.eltrox.core.domain.entity.Customer
import pl.eltrox.core.domain.entity.Product
import pl.eltrox.core.domain.repository.CartRepositoryInterface
import pl.eltrox.core.domain.repository.CustomerRepositoryInterface
import pl.eltrox.core.domain.repository.ProductRepositoryInterface
import pl.eltrox.core.domain.usecase.createcart.CreateCartRequest
import pl.eltrox.core.domain.usecase.createcart.CreateCartUseCase
import spock.lang.Specification

class CreateCartUseCaseSpecs extends Specification {
    private CustomerRepositoryInterface customerRepository;
    private ProductRepositoryInterface productRepository;
    private CartRepositoryInterface cartRepository;

    def setup() {
        def customer1 = new Customer("John", "Doe", 1L)
        def customer2 = new Customer("Jane", "Doe", 2L)
        def customer3 = new Customer("Spike", "Spiegel", 3L)
        def customer4 = new Customer("Geralt", "z Rivii", 4L)

        def product1 = new Product("Kamera", "1", 1L)
        def product2 = new Product("Rejestrator", "2", 2L)
        def product3 = new Product("Dysk", "3", 3L)
        def product4 = new Product("Przewód", "4", 4L)

        def cart1 = new Cart(customer1, product1, 1L)
        def cart2 = new Cart(customer2, product2, 2L)
        def cart3 = new Cart(customer3, product3, 3L)
        def cart4 = new Cart(customer4, product4, 4L)

        customerRepository = new CustomerRepository()
        customerRepository.save(customer1)
        customerRepository.save(customer2)
        customerRepository.save(customer3)
        customerRepository.save(customer4)

        productRepository = new ProductRepository()
        productRepository.save(product1)
        productRepository.save(product2)
        productRepository.save(product3)
        productRepository.save(product4)

        cartRepository = new CartRepository()
        cartRepository.save(cart1)
        cartRepository.save(cart2)
        cartRepository.save(cart3)
        cartRepository.save(cart4)
    }

    def "CreateCartUseCase.execute() should add new Cart to repository"() {
        setup:
        def customerId = 1L
        def productId = 2L
        def request = new CreateCartRequest(customerId, productId)
        def useCase = new CreateCartUseCase(cartRepository, productRepository, customerRepository)
        def product = productRepository.get(productId)

        when:
        def response = useCase.execute(request)
        def cart = cartRepository.get(response.getData().getId())

        then:
        response.getData() != null
        response.hasErrors() == false
        response.getErrors().size() == 0
        cart != null
        cart.getCustomer().getId() == customerId
        cart.isProductInCart(product) == true
    }

    def "CreateCartUseCase.execute() should return response with error if request has no customerId"() {
        setup:
        def customerId = null
        def productId = 2L
        def request = new CreateCartRequest(customerId, productId)
        def useCase = new CreateCartUseCase(cartRepository, productRepository, customerRepository)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }

    def "CreateCartUseCase.execute() should return response with error if request has non existing customerId"() {
        setup:
        def customerId = 5L
        def productId = 2L
        def request = new CreateCartRequest(customerId, productId)
        def useCase = new CreateCartUseCase(cartRepository, productRepository, customerRepository)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }

    def "CreateCartUseCase.execute() should return response with error if request has non existing productId"() {
        setup:
        def customerId = 1L
        def productId = 9L
        def request = new CreateCartRequest(customerId, productId)
        def useCase = new CreateCartUseCase(cartRepository, productRepository, customerRepository)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }
}
