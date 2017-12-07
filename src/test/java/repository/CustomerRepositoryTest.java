package repository;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;
import pl.eltrox.apps.cli.data.CustomerRepository;
import pl.eltrox.core.domain.entity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static com.mscharhag.oleaster.matcher.Matchers.*;

@RunWith(OleasterRunner.class)
public class CustomerRepositoryTest {
    private CustomerRepository customerRepository;

    {
        describe("CustomerRepository", () -> {
            beforeEach(() -> {
                ArrayList<Customer> customers = new ArrayList<>();
                customers.add(new Customer("John", "Doe", 1L));
                customers.add(new Customer("Jane", "Doe", 2L));
                customers.add(new Customer("Spike", "Spiegel", 3L));
                customers.add(new Customer("Geralt", "z Rivii", 4L));

                customerRepository = new CustomerRepository(customers);
            });

            describe("get()", () -> {
                it("should return customer specified by given id", () -> {
                    Long customerId = 1L;
                    Customer customer = customerRepository.get(customerId);

                    expect(customer).toBeNotNull();
                    expect(customer.getId()).toEqual(customerId);
                    expect(customer.getClass()).toEqual(Customer.class);
                });
            });

            describe("all()", () -> {
                it("should return all customers", () -> {
                    Collection<Customer> customers = customerRepository.all();
                    Boolean areAllCustomersIndicesRetrieved = customers.stream().anyMatch(new Predicate<Customer>() {
                        @Override
                        public boolean test(Customer customer) {
                            ArrayList indices = new ArrayList();
                            indices.add(1L);
                            indices.add(2L);
                            indices.add(3L);
                            indices.add(4L);

                            return indices.contains(customer.getId());
                        }
                    });

                    expect(customers.size()).toEqual(4);
                    expect(areAllCustomersIndicesRetrieved).toEqual(true);
                });
            });

            describe("save()", () -> {
                it("should add new Customer if Customer has no id", () -> {
                    Customer customer = new Customer("Abraham", "Lincoln");
                    int beforeSaveCount = customerRepository.count();

                    Customer addedCustomer = customerRepository.save(customer);
                    int afterSaveCount = customerRepository.count();

                    expect(addedCustomer != customer).toBeTrue();
                    expect(customer.getId()).toBeNull();
                    expect(addedCustomer.getId()).toBeNotNull();
                    expect(beforeSaveCount).toEqual(afterSaveCount - 1);
                    expect(customer.getFirstName()).toMatch(addedCustomer.getFirstName());
                    expect(customer.getLastName()).toMatch(addedCustomer.getLastName());
                });

                it("should modify Customer if given Customer with existing id", () -> {
                    Customer customer = customerRepository.get(1L);
                    String newName = "Daniel";
                    int beforeSaveCount = customerRepository.count();

                    customer.setFirstName(newName);
                    Customer modifiedCustomer = customerRepository.save(customer);
                    int afterSaveCount = customerRepository.count();

                    expect(customer != modifiedCustomer).toBeTrue();
                    expect(modifiedCustomer.getFirstName()).toMatch(newName);
                    expect(beforeSaveCount).toEqual(afterSaveCount);
                });

                it("should add new Customer if given Customer with not existing id", () -> {
                    Long customerId = 99L;
                    Customer customer = new Customer("Abraham", "Lincoln", customerId);
                    int beforeSaveCount = customerRepository.count();

                    Customer addedCustomer = customerRepository.save(customer);
                    int afterSaveCount = customerRepository.count();

                    expect(addedCustomer != customer).toBeTrue();

                    expect(customer.getId()).toEqual(addedCustomer.getId());
                    expect(customer.getFirstName()).toMatch(addedCustomer.getFirstName());
                    expect(customer.getLastName()).toMatch(addedCustomer.getLastName());

                    expect(beforeSaveCount).toEqual(afterSaveCount - 1);
                });

                it("should not save Customer if Customer is null", () -> {
                    Customer customer = null;
                    int beforeSaveCount = customerRepository.count();

                    customer = customerRepository.save(customer);
                    int afterSaveCount = customerRepository.count();

                    expect(customer).toBeNull();
                    expect(beforeSaveCount).toEqual(afterSaveCount);
                });
            });
        });
    }
}
