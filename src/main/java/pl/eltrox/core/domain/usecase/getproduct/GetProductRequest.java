package pl.eltrox.core.domain.usecase.getproduct;

import pl.eltrox.core.domain.usecase.RequestInterface;

public class GetProductRequest implements RequestInterface {
    private long id;

    public GetProductRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
