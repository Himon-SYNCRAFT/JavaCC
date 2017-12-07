package repository

import pl.eltrox.apps.cli.data.CustomerRepository
import pl.eltrox.core.domain.entity.Customer
import spock.lang.Specification

class CustomerRepositorySpecs extends Specification {
    private CustomerRepository customerRepository

    def setup() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", "Doe", 1L));
        customers.add(new Customer("Jane", "Doe", 2L));
        customers.add(new Customer("Spike", "Spiegel", 3L));
        customers.add(new Customer("Geralt", "z Rivii", 4L));

        customerRepository = new CustomerRepository(customers)
    }

    def "CustomerRepository.get() should return customer identified by given id"() {
        when:
        def customer = customerRepository.get(customerId)

        then:
        customer != null
        customer.id == customerId
        customer.getClass() == Customer

        where:
        customerId | _
        1L | _
        2L | _
    }

    def "CustomerRepository.get() should return null if given not existing id"() {
        when:
        def customer = customerRepository.get(customerId)

        then:
        customer == null

        where:
        customerId | _
        10L | _
        200L | _
    }

    def "CustomerRepository.all() should return all customers"() {
        when:
        def customers = customerRepository.all()

        then:
        customers.size() == 4
        customers.each { it.getClass() == Customer }
        customers.each { [1L, 2L, 3L, 4L].contains(it.id) }
    }

    def "CustomerRepository.save() add new Customer if given Customer without id"() {
        setup:
        def customer = new Customer("Abraham", "Lincoln")
        int beforeSaveCount = customerRepository.count()

        when:
        def addedCustomer = customerRepository.save(customer)
        int afterSaveCount = customerRepository.count()

        then:
        addedCustomer != customer
        customer.id == null
        addedCustomer.id != null
        beforeSaveCount == afterSaveCount - 1

        customer.firstName == addedCustomer.firstName
        customer.lastName == addedCustomer.lastName
    }

    def "CustomerRepository.save() should modify Customer if given Customer with existing id"() {
        setup:
        def customer = customerRepository.get(1L)
        String newName = "Daniel"
        int beforeSaveCount = customerRepository.count()

        when:
        customer.setFirstName(newName)
        def modifiedCustomer = customerRepository.save(customer)
        int afterSaveCount = customerRepository.count()

        then:
        customer != modifiedCustomer
        modifiedCustomer.firstName == newName
        beforeSaveCount == afterSaveCount
    }

    def "CustomerRepository.save() should not save null Customer"() {
        setup:
        def customer = null
        int beforeSaveCount = customerRepository.count()

        when:
        customer = customerRepository.save(customer)
        int afterSaveCount = customerRepository.count()

        then:
        customer == null
        beforeSaveCount == afterSaveCount
    }

    def "CustomerRepository.save() should add new Customer if given Customer with not existing id"() {
        setup:
        Long customerId = 99L
        Customer customer = new Customer("Abraham", "Lincoln", customerId)
        int beforeSaveCount = customerRepository.count()

        when:
        Customer addedCustomer = customerRepository.save(customer)
        int afterSaveCount = customerRepository.count()

        then:
        addedCustomer != customer

        customer.getId() == addedCustomer.getId()
        customer.getFirstName() == addedCustomer.getFirstName()
        customer.getLastName() == addedCustomer.getLastName()

        beforeSaveCount == afterSaveCount - 1
    }
}
