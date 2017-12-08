package pl.eltrox.core.domain.entity;

import java.util.Objects;

public class Customer extends Entity {
    private String firstName;
    private String lastName;

    public Customer(String firstName, String lastName, Long id) {
        this(firstName, lastName);
        this.id = id;
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        id = null;
    }

    @Override
    public Customer clone() {
        return new Customer(this.firstName, this.lastName, this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
