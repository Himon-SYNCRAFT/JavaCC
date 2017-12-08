package pl.eltrox.core.domain.usecase.getproduct;

import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.repository.ProductRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

public class GetProductUseCase implements UseCaseInterface<GetProductResponse, GetProductRequest> {
    private final ProductRepositoryInterface productRepository;

    public GetProductUseCase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public GetProductResponse execute(GetProductRequest request) {
        Long productId = request.getId();
        Product product = productRepository.get(productId);

        GetProductResponse response = new GetProductResponse();

        if (product == null) {
            response.addError(String.format("Product with id %s not found", productId));
        } else {
            response.setData(product);
        }

        return response;
    }
}
