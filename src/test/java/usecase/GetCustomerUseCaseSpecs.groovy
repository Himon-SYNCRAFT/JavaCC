package usecase

import pl.eltrox.data.memory.CustomerRepository
import pl.eltrox.core.domain.entity.Customer
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerRequest
import pl.eltrox.core.domain.usecase.getcustomer.GetCustomerUseCase
import spock.lang.Specification

class GetCustomerUseCaseSpecs extends Specification {
    private CustomerRepository customerRepository;

    def setup() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", "Doe", 1L));
        customers.add(new Customer("Jane", "Doe", 2L));
        customers.add(new Customer("Spike", "Spiegel", 3L));
        customers.add(new Customer("Geralt", "z Rivii", 4L));

        customerRepository = new CustomerRepository(customers)
    }

    def "GetCustomerUseCase.execute() return response with customer if given existing id"() {
        setup:
        def customerId = 1L
        def useCase = new GetCustomerUseCase(customerRepository)
        def request = new GetCustomerRequest(customerId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData().id == customerId
        response.hasErrors() == false
        response.getErrors().size() == 0
    }

    def "GetCustomerUseCase.execute() return response with error if customer cannot be find"() {
        setup:
        def nonExistingCustomerId = 10L
        def useCase = new GetCustomerUseCase(customerRepository)
        def request = new GetCustomerRequest(nonExistingCustomerId)

        when:
        def response = useCase.execute(request)

        then:
        response.getData() == null
        response.hasErrors() == true
        response.getErrors().size() == 1
    }
}
