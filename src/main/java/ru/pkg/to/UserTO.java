package ru.pkg.to;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Objects;

public class UserTO {

    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 25)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 25)
    private String surname;

    @NotEmpty
    @Size(min = 5, max = 64)
    private String password;

    public UserTO() {
    }

    public UserTO(Integer id, String name, String surname, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTO that = (UserTO) o;

        return Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.surname, that.surname)
                && Objects.equals(this.password, that.password);
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
