package pl.eltrox.apps.webapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.usecase.UseCaseFactory;
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerRequest;
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerResponse;
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerUseCase;

@RestController
public class CustomerController {

    @RequestMapping("/customer")
    public Customer getCustomer(@RequestParam(value="id") Long id) {
        GetCustomerUseCase useCase = UseCaseFactory.GetCustomerUseCase();
        GetCustomerRequest request = new GetCustomerRequest(id);
        GetCustomerResponse response = useCase.execute(request);

        return response.getData();
    }
}
