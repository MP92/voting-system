package ru.pkg.to;

import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;

import java.util.List;

public class RestaurantWithVotes {

    private final Integer id;

    private final String name;

    private final String description;

    private final String address;

    private final String phoneNumber;

    private final List<Dish> menu;

    private final int votes;

    public RestaurantWithVotes(Restaurant restaurant, Integer votes) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.address = restaurant.getAddress();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.menu = restaurant.getMenu();
        this.votes = votes != null ? votes : 0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public int getVotes() {
        return votes;
    }
}
