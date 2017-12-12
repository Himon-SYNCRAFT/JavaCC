package pl.eltrox.core.domain.usecase.findproduct;

import pl.eltrox.core.domain.usecase.RequestInterface;

import java.util.HashMap;
import java.util.Map;

public class FindProductRequest implements RequestInterface {
    private Long id;
    private String sku;
    private String name;

    public FindProductRequest(Map filter) {
        id = (Long) filter.get("id");
        sku = (String) filter.get("sku");
        name = (String) filter.get("name");
    }

    public Map getFilter() {
        Map filter = new HashMap();
        filter.put("id", id);
        filter.put("sku", sku);
        filter.put("name", name);

        return filter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id != null) {
            return;
        }

        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        if (this.sku != null) {
            return;
        }

        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name != null) {
            return;
        }

        this.name = name;
    }
}
