package usecase;

import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface
import pl.eltrox.core.domain.usecase.findproduct.FindProductRequest
import pl.eltrox.core.domain.usecase.findproduct.FindProductUseCase;
import pl.eltrox.data.fakeverto.ProductRepository;
import spock.lang.Specification;

class FindProductUseCaseSpecs extends Specification {
    private ProductRepositoryInterface productRepository

    def setup() {
        productRepository =  new ProductRepository()
        productRepository.save(new Product("Kamera", "1", 1L))
        productRepository.save(new Product("Rejestrator", "2", 2L))
        productRepository.save(new Product("Dysk", "3", 3L))
        productRepository.save(new Product("Przewód", "4", 4L))
    }

    def "FindProductUseCase.execute() should return collection of Products filtered by given filter"() {
        setup:
        def useCase = new FindProductUseCase(productRepository)
        Map filter = new HashMap()
        filter.put(filterName, filterValue)
        def request = new FindProductRequest(filter)

        when:
        def response = useCase.execute(request)

        then:
        !response.hasErrors()
        response.getErrors().size() == 0

        response.getData().each {
            if (filterName == "name") {
                it.name.toLowerCase().contains(filterValue.toString().toLowerCase())
            } else if (filterName == "id") {
                it.id  == filterValue
            } else if (filterName == "sku") {
                it.sku.toLowerCase().contains(filterValue.toString().toLowerCase())
            }
        }

        where:
        filterName | filterValue
        "name" | "Przewód"
        "id" | 1L
        "sku" | "2"
    }

    def "FindProductUseCase.execute() should return empty collection of Products if cannot find given filter"() {
        setup:
        def useCase = new FindProductUseCase(productRepository)
        Map filter = new HashMap()
        filter.put(filterName, filterValue)
        def request = new FindProductRequest(filter)

        when:
        def response = useCase.execute(request)

        then:
        !response.hasErrors()
        response.getErrors().size() == 0
        response.getData().size() == 0

        where:
        filterName | filterValue
        "name" | "Samochód"
        "id" | 99L
        "sku" | "Asdfsd"
    }
}
