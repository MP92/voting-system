package ru.pkg.to;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static ru.pkg.utils.constants.EntityConstraints.*;

public class RestaurantTO {

    private Integer id;

    @NotEmpty
    @Size(min = NAME_MIN, max = NAME_MAX)
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    public RestaurantTO() {
    }

    public RestaurantTO(Integer id, String name, String description, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isNew() {
        return id == null;
    }
}
