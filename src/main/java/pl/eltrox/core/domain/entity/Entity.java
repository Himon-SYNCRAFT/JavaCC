package pl.eltrox.core.domain.entity;

public abstract class Entity implements Cloneable {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    @Override
    public abstract Entity clone();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
