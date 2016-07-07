package ru.pkg.to;

import org.hibernate.validator.constraints.NotEmpty;
import ru.pkg.model.DishCategory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.pkg.utils.constants.EntityConstraints.*;

/**
 * Transfer object for storing dish form data
 */
public class DishTO {

    private Integer id;

    @NotEmpty
    @Size(min = NAME_MIN, max = NAME_MAX)
    private String name;

    @NotEmpty
    private String description;

    @Min(DISH_WEIGHT_MIN)
    @Max(DISH_WEIGHT_MAX)
    @NotNull
    private Integer weight;

    @NotNull
    private DishCategory category;

    @Min(DISH_PRICE_MIN)
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
