package pl.eltrox.core.domain.usecase;

import java.util.List;

public interface ResponseInterface<Data> {
    public boolean hasErrors();
    public List getErrors();
    public Data getData();
    public void addError(String error);
}
