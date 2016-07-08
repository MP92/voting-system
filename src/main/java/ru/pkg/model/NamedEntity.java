package ru.pkg.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import static ru.pkg.utils.constants.EntityConstraints.*;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NamedEntity extends BaseEntity {

    @NotEmpty
    @Column(name = "name", nullable = false)
    @Size(min = NAME_MIN, max = NAME_MAX)
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
