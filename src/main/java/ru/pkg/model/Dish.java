package ru.pkg.model;

public class Dish extends BaseEntity {

    private Long price;

    private String description;

    public Dish(Integer id, String description, Long price) {
        super(id);
        this.price = price;
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
