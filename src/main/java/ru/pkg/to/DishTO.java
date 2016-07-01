package ru.pkg.to;

import org.hibernate.validator.constraints.NotEmpty;
import ru.pkg.model.DishCategory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Transfer object for storing dish form data
 */
public class DishTO {

    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @NotEmpty
    private String description;

    @Min(50)
    @Max(500)
    @NotNull
    private Integer weight;

    @NotNull
    private DishCategory category;

    @Min(1)
    @NotNull
    private Double price;

    private boolean inMenu;

    public DishTO() {
    }

    public DishTO(Integer id, String name, String description, int weight, DishCategory category, double price, boolean inMenu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.category = category;
        this.price = price;
        this.inMenu = inMenu;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public boolean isNew() {
        return id == null;
    }
}
