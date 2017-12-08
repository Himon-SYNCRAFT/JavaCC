package pl.eltrox.core.domain.usecase.getcustomer;

import pl.eltrox.core.domain.entity.Customer;
import pl.eltrox.core.domain.repository.CustomerRepositoryInterface;
import pl.eltrox.core.domain.usecase.UseCaseInterface;

public class GetCustomerUseCase implements UseCaseInterface<GetCustomerResponse, GetCustomerRequest> {
    private final CustomerRepositoryInterface customerRepository;

    public GetCustomerUseCase(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public GetCustomerResponse execute(GetCustomerRequest request) {
        Long customerId = request.getId();
        Customer customer = customerRepository.get(customerId);

        GetCustomerResponse response = new GetCustomerResponse();

        if (customer == null) {
            response.addError(String.format("Customer with id %s not found", customerId));
        } else {
            response.setData(customer);
        }

        return response;
    }
}
