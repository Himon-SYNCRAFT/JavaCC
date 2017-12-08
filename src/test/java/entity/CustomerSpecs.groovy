package entity

import pl.eltrox.core.domain.entity.Customer
import spock.lang.Specification

class CustomerSpecs extends Specification {
    def "Customer.clone() copy all fields to new Customer object"() {
        when:
        def clonedCustomer = customer.clone()

        then:
        !customer.is(clonedCustomer)
        customer.firstName.equals(clonedCustomer.firstName)
        customer.lastName.equals(clonedCustomer.lastName)
        customer.id.equals(clonedCustomer.id)

        where:
        customer                                 | _
        new Customer("fistName", "lastName", 1L) | _
        new Customer("fistName", "lastName")     | _
    }

    def "Customer.equals() should return true if all fields are same"() {
        setup:
        def firstName = "firstName"
        def lastName = "lastName"
        def id = 1L

        def customer1 = new Customer(firstName, lastName, id)
        def customer2 = new Customer(firstName, lastName, id)

        when:
        def isEqual = customer1.equals(customer2)

        then:
        !customer1.is(customer2)
        isEqual == true
    }
}
