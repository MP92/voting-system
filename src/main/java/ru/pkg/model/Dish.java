package ru.pkg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "dishes", uniqueConstraints = @UniqueConstraint(name = "dishes_unique_name_idx", columnNames = {"restaurant_id", "name"}))
public class Dish extends NamedEntity {

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @Column(name = "weight", nullable = false)
    @Min(50)
    @Max(500)
    private int weight;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DishCategory category;

    @Column(name = "price", nullable = false, columnDefinition = "NUMERIC(7, 2) NOT NULL")
    @Min(1)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "in_menu", nullable = false)
    private boolean inMenu;

    public Dish() {
    }

    public Dish(Integer id, String name, String description, int weight, DishCategory category, double price, boolean inMenu/*, int restaurantId*/) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.category = category;
        this.price = price;
        this.inMenu = inMenu;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
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
                ", inMenu=" + inMenu +
                '}';
    }
}
