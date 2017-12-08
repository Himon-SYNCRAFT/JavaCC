package pl.eltrox.apps.webapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.usecase.UseCaseFactory;
import pl.eltrox.core.domain.usecase.getproduct.GetProductRequest;
import pl.eltrox.core.domain.usecase.getproduct.GetProductResponse;
import pl.eltrox.core.domain.usecase.getproduct.GetProductUseCase;

@RestController
public class ProductController {
    @RequestMapping("/product")
    public Product getProduct(@RequestParam(value="id") Long id) {
        GetProductUseCase useCase = UseCaseFactory.GetProductUseCase();
        GetProductRequest request = new GetProductRequest(id);
        GetProductResponse response = useCase.execute(request);

        return response.getData();
    }
}
