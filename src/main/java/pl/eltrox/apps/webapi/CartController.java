package pl.eltrox.apps.webapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.eltrox.core.domain.entity.Cart;
import pl.eltrox.core.domain.usecase.UseCaseFactory;
import pl.eltrox.core.domain.usecase.getcart.GetCartRequest;
import pl.eltrox.core.domain.usecase.getcart.GetCartResponse;
import pl.eltrox.core.domain.usecase.getcart.GetCartUseCase;

@RestController
public class CartController {
    @RequestMapping("/cart")
    public Cart getCart(@RequestParam(value="id") Long id) {
        GetCartUseCase useCase = UseCaseFactory.GetCartUseCase();
        GetCartRequest request = new GetCartRequest(id);
        GetCartResponse response = useCase.execute(request);

        return response.getData();
    }
}
