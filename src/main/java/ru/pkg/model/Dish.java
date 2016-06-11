package ru.pkg.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

public class Dish extends NamedEntity {

    @NotEmpty
    private String description;

    @Min(50)
    @Max(500)
    private int weight;

    @NotNull
    private DishCategory category;

    @Min(1)
    private double price;

    private int restaurantId;

    public Dish() {
    }

    public Dish(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Dish(Integer id, String name, String description, int weight, DishCategory category, double price, int restaurantId) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.category = category;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", description=" + description +
                ", weight=" + weight +
                ", category=" + category +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
