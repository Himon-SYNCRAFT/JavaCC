package repository

import pl.eltrox.data.memory.CartRepository
import pl.eltrox.data.memory.CustomerRepository
import pl.eltrox.data.memory.ProductRepository
import pl.eltrox.core.domain.entity.Cart
import pl.eltrox.core.domain.entity.Product
import spock.lang.Specification

class CartRepositorySpecs extends Specification {
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

    def "CartRepository.get() should return Cart identified by given id"() {
        when:
        def cart = cartRepository.get(cartId)

        then:
        cart != null
        cart.getId() == cartId
        cart.getClass() == Cart

        where:
        cartId | _
        1L        | _
        2L        | _
    }

    def "CartRepository.get() should return null if given not existing id"() {
        when:
        def cart = cartRepository.get(cartId)

        then:
        cart == null

        where:
        cartId | _
        10L | _
        200L | _
    }

    def "CartRepository.all() should return all carts"() {
        when:
        def carts = cartRepository.all()

        then:
        carts.size() == 2
        carts.each { it.getClass() == Cart }
        carts.each { [1L, 2L, 3L, 4L].contains(it.id) }
    }

    def "CartRepository.save() add new Cart if given Cart without id"() {
        setup:
        def cart = new Cart(null)
        int beforeSaveCount = cartRepository.count()

        when:
        def addedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        !addedCart.is(cart)
        cart.id == null
        addedCart.id != null
        beforeSaveCount == afterSaveCount - 1
    }

    def "CartRepository.save() should modify Cart if given Cart with existing id"() {
        setup:
        def cart = cartRepository.get(1L)
        int beforeSaveCount = cartRepository.count()
        def product = new Product("new product", "234345", 5L)

        when:
        cart.addProduct(product)
        def modifiedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        !cart.is(modifiedCart)
        modifiedCart.isProductInCart(product) == true
        beforeSaveCount == afterSaveCount
    }

    def "CartRepository.save() should not save null Cart"() {
        setup:
        def cart = null
        int beforeSaveCount = cartRepository.count()

        when:
        cart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        cart == null
        beforeSaveCount == afterSaveCount
    }

    def "CartRepository.save() should add new Cart if given Cart with not existing id"() {
        setup:
        Long cartId = 99L
        def cart = new Cart(null, null, cartId)
        int beforeSaveCount = cartRepository.count()

        when:
        def addedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        !addedCart.is(cart)

        cart.getId() == addedCart.getId()
        beforeSaveCount == afterSaveCount - 1
    }
}
