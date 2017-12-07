package repository

import pl.eltrox.apps.cli.data.ProductRepository
import pl.eltrox.core.domain.entity.Product
import spock.lang.Specification

class ProductRepositorySpecs extends Specification {
    private ProductRepository productRepository;

    def setup() {
        productRepository =  new ProductRepository()
        productRepository.save(new Product("Kamera", "1", 1L))
        productRepository.save(new Product("Rejestrator", "2", 2L))
        productRepository.save(new Product("Dysk", "3", 3L))
        productRepository.save(new Product("Przewód", "4", 4L))
    }

    def "ProductRepository.get() should return Product identified by given id"() {
        when:
        def product = productRepository.get(productId)

        then:
        product != null
        product.id == productId
        product.getClass() == Product

        where:
        productId | _
        1L        | _
        2L        | _
    }

    def "ProductRepository.get() should return null if given not existing id"() {
        when:
        def product = productRepository.get(productId)

        then:
        product == null

        where:
        productId | _
        10L | _
        200L | _
    }

    def "ProductRepository.all() should return all products"() {
        when:
        def products = productRepository.all()

        then:
        products.size() == 4
        products.each { it.getClass() == Product }
        products.each { [1L, 2L, 3L, 4L].contains(it.id) }
    }

    def "ProductRepository.save() add new Product if given Product without id"() {
        setup:
        def product = new Product("Camera", "1234")
        int beforeSaveCount = productRepository.count()

        when:
        def addedProduct = productRepository.save(product)
        int afterSaveCount = productRepository.count()

        then:
        addedProduct != product
        product.id == null
        addedProduct.id != null
        beforeSaveCount == afterSaveCount - 1

        product.name == addedProduct.name
        product.sku == addedProduct.sku
    }

    def "ProductRepository.save() should modify Product if given Product with existing id"() {
        setup:
        def product = productRepository.get(1L)
        String newName = "Camera"
        int beforeSaveCount = productRepository.count()

        when:
        product.setName(newName)
        def modifiedProduct = productRepository.save(product)
        int afterSaveCount = productRepository.count()

        then:
        product != modifiedProduct
        modifiedProduct.name == newName
        beforeSaveCount == afterSaveCount
    }

    def "ProductRepository.save() should not save null Product"() {
        setup:
        def product = null
        int beforeSaveCount = productRepository.count()

        when:
        product = productRepository.save(product)
        int afterSaveCount = productRepository.count()

        then:
        product == null
        beforeSaveCount == afterSaveCount
    }

    def "ProductRepository.save() should add new Product if given Product with not existing id"() {
        setup:
        Long productId = 99L
        Product product = new Product("Camera", "124", productId)
        int beforeSaveCount = productRepository.count()

        when:
        Product addedProduct = productRepository.save(product)
        int afterSaveCount = productRepository.count()

        then:
        addedProduct != product

        product.getId() == addedProduct.getId()
        product.getName() == addedProduct.getName()
        product.getSku() == addedProduct.getSku()

        beforeSaveCount == afterSaveCount - 1
    }
}
