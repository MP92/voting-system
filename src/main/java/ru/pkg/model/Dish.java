package ru.pkg.model;

public class Dish extends NamedEntity {

    private String description;

    private int weight;

    private DishCategory category;

    private double price;

    public Dish(Integer id, String name, String description, int weight, DishCategory category, double price) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.category = category;
        this.price = price;
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

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", description=" + description +
                ", weight=" + weight +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
