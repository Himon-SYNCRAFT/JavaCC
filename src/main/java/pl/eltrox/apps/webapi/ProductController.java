package pl.eltrox.apps.webapi;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.eltrox.core.domain.entity.Product;
import pl.eltrox.core.domain.usecase.UseCaseFactory;
import pl.eltrox.core.domain.usecase.findproduct.FindProductRequest;
import pl.eltrox.core.domain.usecase.findproduct.FindProductResponse;
import pl.eltrox.core.domain.usecase.findproduct.FindProductUseCase;
import pl.eltrox.core.domain.usecase.getproduct.GetProductRequest;
import pl.eltrox.core.domain.usecase.getproduct.GetProductResponse;
import pl.eltrox.core.domain.usecase.getproduct.GetProductUseCase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {
    @RequestMapping(value="/product/{id}")
    public Product getProduct(@PathVariable(value="id") Long id) {
        GetProductUseCase useCase = UseCaseFactory.GetProductUseCase();
        GetProductRequest request = new GetProductRequest(id);
        GetProductResponse response = useCase.execute(request);

        return response.getData();
    }

    @RequestMapping("/product/find")
    public Collection<Product> findProducts(@RequestParam(value = "id", required = false) Long id,
                                            @RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "sku", required = false) String sku) {
        FindProductUseCase useCase = UseCaseFactory.FindProductUseCase();
        Map filter = new HashMap<>();

        if (id != null) {
            filter.put("id", id);
        }

        if (name != null) {
            filter.put("name", name);
        }

        if (sku != null) {
            filter.put("sku", sku);
        }

        FindProductRequest request = new FindProductRequest(filter);
        FindProductResponse response = useCase.execute(request);

        return response.getData();
    }
}
