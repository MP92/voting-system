package ru.pkg.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotEmpty
    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 100)
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
