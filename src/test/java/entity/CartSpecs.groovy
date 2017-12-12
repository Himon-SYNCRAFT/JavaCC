package entity

import pl.eltrox.core.domain.entity.Cart
import pl.eltrox.core.domain.entity.Customer
import pl.eltrox.core.domain.entity.Product
import spock.lang.Specification

class CartSpecs extends Specification {
    def "Cart.clone() copy all fields to new Cart object"() {
        when:
        def clonedCart = cart.clone()

        then:
        !cart.is(clonedCart)
        cart.customer.equals(clonedCart.customer)
        cart.items.equals(clonedCart.items)
        cart.id.equals(clonedCart.id)

        where:
        customer << [
                new Customer("fistName","lastName", 1L),
                null,
                new Customer("fistName","lastName", 1L),
                null,
        ]
        product << [
                null,
                new Product("name", "1234", 1L),
                new Product("name2", "2345"),
                null,
        ]
        id << [
                null,
                null,
                1L,
                null,
        ]
        cart << [
                new Cart(customer),
                new Cart(customer, product),
                new Cart(customer, product, id),
                new Cart(customer, product, id)
        ]
    }

    def "Cart.equals() should return true if all fields are same"() {
        setup:
        def customer = new Customer("firstName", "lastName", 1L)
        def product = new Product("camera", "1234", 1L)
        def cart1 = new Cart(customer, product)
        def cart2 = new Cart(customer, product)

        when:
        def isEqual = cart1.equals(cart2)

        then:
        !cart1.is(cart2)
        isEqual == true
    }
}
