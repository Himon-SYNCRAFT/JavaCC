package pl.eltrox.core.domain.usecase.getcustomer;

import pl.eltrox.core.domain.usecase.RequestInterface;

public class GetCustomerRequest implements RequestInterface {
    private long id;

    public GetCustomerRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
