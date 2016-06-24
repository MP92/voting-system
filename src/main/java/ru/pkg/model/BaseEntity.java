package ru.pkg.model;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity {

    //todo correct strategy for id generation?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
/*    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")*/
    private Integer id;

    protected BaseEntity() {
    }

    protected BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return !(this.id == null || that.id == null) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
