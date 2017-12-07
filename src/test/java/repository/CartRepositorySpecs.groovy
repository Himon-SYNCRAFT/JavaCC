package repository

import pl.eltrox.apps.cli.data.CartRepository
import pl.eltrox.apps.cli.data.CustomerRepository
import pl.eltrox.apps.cli.data.ProductRepository
import pl.eltrox.core.domain.entity.Cart
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
        cart.id == cartId
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
        carts.size() == 4
        carts.each { it.getClass() == Cart }
        carts.each { [1L, 2L, 3L, 4L].contains(it.id) }
    }

    def "CartRepository.save() add new Cart if given Cart without id"() {
        setup:
        def cart = new Cart("Camera", "1234")
        int beforeSaveCount = cartRepository.count()

        when:
        def addedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        addedCart != cart
        cart.id == null
        addedCart.id != null
        beforeSaveCount == afterSaveCount - 1

        cart.name == addedCart.name
        cart.sku == addedCart.sku
    }

    def "CartRepository.save() should modify Cart if given Cart with existing id"() {
        setup:
        def cart = cartRepository.get(1L)
        String newName = "Camera"
        int beforeSaveCount = cartRepository.count()

        when:
        cart.setName(newName)
        def modifiedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        cart != modifiedCart
        modifiedCart.name == newName
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
        Cart cart = new Cart("Camera", "124", cartId)
        int beforeSaveCount = cartRepository.count()

        when:
        Cart addedCart = cartRepository.save(cart)
        int afterSaveCount = cartRepository.count()

        then:
        addedCart != cart

        cart.getId() == addedCart.getId()
        cart.getName() == addedCart.getName()
        cart.getSku() == addedCart.getSku()

        beforeSaveCount == afterSaveCount - 1
    }
}
