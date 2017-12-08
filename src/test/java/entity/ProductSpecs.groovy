package entity

import pl.eltrox.core.domain.entity.Product
import spock.lang.Specification

class ProductSpecs extends Specification {

    def "Product.clone() copy all fields to new Product object"() {
        when:
        def clonedProduct = product.clone()

        then:
        !product.is(clonedProduct)
        product.name.equals(clonedProduct.name)
        product.sku.equals(clonedProduct.sku)
        product.id.equals(clonedProduct.id)

        where:
        product | _
        new Product("name", "1234", 1L) | _
        new Product("name2", "2345") | _
    }

    def "Product.equals() should return true if all fields are same"() {
        setup:
        def name = "name"
        def sku = "1234"
        def id = 1L

        def product1 = new Product(name, sku, id)
        def product2 = new Product(name, sku, id)

        when:
        def isEqual = product1.equals(product2)

        then:
        !product1.is(product2)
        isEqual == true
    }
}
