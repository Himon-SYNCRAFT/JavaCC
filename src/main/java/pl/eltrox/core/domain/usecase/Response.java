package pl.eltrox.core.domain.usecase;

import java.util.ArrayList;
import java.util.List;

public abstract class Response<Data> implements ResponseInterface<Data> {
    private ArrayList<String> errors = new ArrayList<>();
    private Data data;

    public Response(Data data) {
        this.data = data;
    }

    @Override
    public boolean hasErrors() {
        return errors.size() > 0;
    }

    @Override
    public List getErrors() {
        return errors;
    }

    @Override
    public void addError(String error) {
        errors.add(error);
    }

    @Override
    public Data getData() {
        return data;
    }
}
