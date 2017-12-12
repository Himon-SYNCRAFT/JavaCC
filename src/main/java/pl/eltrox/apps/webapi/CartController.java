package pl.eltrox.apps.webapi;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.usecase.UseCaseFactory;
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartRequest;
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartResponse;
import pl.eltrox.core.domain.usecase.addproducttocart.AddProductToCartUseCase;
import pl.eltrox.core.domain.usecase.createcart.CreateCartRequest;
import pl.eltrox.core.domain.usecase.createcart.CreateCartResponse;
import pl.eltrox.core.domain.usecase.createcart.CreateCartUseCase;
import pl.eltrox.core.domain.usecase.getcart.GetCartRequest;
import pl.eltrox.core.domain.usecase.getcart.GetCartResponse;
import pl.eltrox.core.domain.usecase.getcart.GetCartUseCase;

@RestController
public class CartController {
    @RequestMapping("/cart/{id}")
    public Cart getCart(@PathVariable(value="id") Long id) {
        GetCartUseCase useCase = UseCaseFactory.GetCartUseCase();
        GetCartRequest request = new GetCartRequest(id);
        GetCartResponse response = useCase.execute(request);

        return response.getData();
    }

    @RequestMapping("/cart/new")
    public Cart createCart(@RequestParam(value = "customerId") Long customerId) {
        CreateCartUseCase useCase = UseCaseFactory.CreateCartUseCase();
        CreateCartRequest request = new CreateCartRequest(customerId);
        CreateCartResponse response = useCase.execute(request);

        return response.getData();
    }

    @RequestMapping("/cart/{cartId}/add/product/{productId}")
    public Cart addProductToCart(@PathVariable(value = "cartId") Long cartId,
                                 @PathVariable(value = "productId") Long productId) {
        AddProductToCartUseCase useCase = UseCaseFactory.AddProductToCartUseCase();
        AddProductToCartRequest request = new AddProductToCartRequest(cartId, productId);
        AddProductToCartResponse response = useCase.execute(request);

        return response.getData();
    }
}
