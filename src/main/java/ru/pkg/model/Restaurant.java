package ru.pkg.model;

import java.util.Set;

public class Restaurant extends NamedEntity {

    private Set<Dish> menu;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
