package pl.eltrox.core.domain.usecase;

public interface UseCaseInterface<Response, Request> {
    public Response execute(Request request);
}
