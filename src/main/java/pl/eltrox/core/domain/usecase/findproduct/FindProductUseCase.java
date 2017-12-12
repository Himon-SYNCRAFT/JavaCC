package pl.eltrox.core.domain.usecase.findproduct;

import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

import java.util.Collection;
import java.util.Map;

public class FindProductUseCase implements UseCaseInterface<FindProductResponse, FindProductRequest> {
    private final ProductRepositoryInterface productRepository;

    public FindProductUseCase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public FindProductResponse execute(FindProductRequest request) {
        Map filter = request.getFilter();
        Collection<Product> products = productRepository.find(filter);

        FindProductResponse response = new FindProductResponse();
        response.setData(products);
        return response;
    }
}
