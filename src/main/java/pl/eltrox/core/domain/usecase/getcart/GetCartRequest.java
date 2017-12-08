package pl.eltrox.core.domain.usecase.getcart;

import pl.eltrox.core.domain.usecase.RequestInterface;

public class GetCartRequest implements RequestInterface {
    private long id;

    public GetCartRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
