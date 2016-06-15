package ru.pkg.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant extends NamedEntity {

    @NotEmpty
    private String description;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    private int votes;

    private List<Dish> menu = Collections.emptyList();

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber) {
        this(id, name, description, address, phoneNumber, null, null);
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber, Integer votes) {
        this(id, name, description, address, phoneNumber, votes, null);
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber, List<Dish> menu) {
        this(id, name, description, address, phoneNumber, null, menu);
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber, Integer votes, List<Dish> menu) {
        super(id, name);
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.votes = votes != null ? votes : 0;
        if (menu != null) {
            this.menu = new ArrayList<>(menu);
        }
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

    public List<Dish> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu != null ? new ArrayList<>(menu) : Collections.emptyList();
    }

    public void addDishToMenu(Dish dish) {
        menu.add(dish);
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", votes=" + votes +
                ", menu=" + menu +
                '}';
    }
}
