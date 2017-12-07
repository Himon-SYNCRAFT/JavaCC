package pl.eltrox.core.domain.entity;

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
    }

    @Override
    public Customer clone() {
        return new Customer(this.firstName, this.lastName, this.id);
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
