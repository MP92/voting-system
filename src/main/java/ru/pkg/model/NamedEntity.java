package ru.pkg.model;

import org.hibernate.validator.constraints.NotEmpty;

public class NamedEntity extends BaseEntity {

    @NotEmpty
    private String name;

    protected NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
