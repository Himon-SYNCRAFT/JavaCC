package usecase

import pl.eltrox.data.memory.ProductRepository
import pl.eltrox.core.domain.entity.Product
import pl.eltrox.core.domain.repository.ProductRepositoryInterface
import pl.eltrox.core.domain.usecase.getproduct.GetProductRequest
import pl.eltrox.core.domain.usecase.getproduct.GetProductUseCase
import spock.lang.Specification

class GetProductUseCaseSpecs extends Specification {
    private ProductRepositoryInterface productRepository;

    def setup() {
        productRepository =  new ProductRepository()
        productRepository.save(new Product("Kamera", "1", 1L))
        productRepository.save(new Product("Rejestrator", "2", 2L))
        productRepository.save(new Product("Dysk", "3", 3L))
        productRepository.save(new Product("Przewód", "4", 4L))
    }

    def "GetProductUseCase.execute() should return response with Product if given existing id"() {
        setup:
        def productId = 1L
        def useCase = new GetProductUseCase(productRepository)
        def request = new GetProductRequest(productId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData().id == productId
        response.hasErrors() == false
        response.getErrors().size() == 0
    }

    def "GetProductUseCase.execute() should return response with error if given non existing id"() {
        setup:
        def nonExistingProductId = 99L
        def useCase = new GetProductUseCase(productRepository)
        def request = new GetProductRequest(nonExistingProductId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }
}
