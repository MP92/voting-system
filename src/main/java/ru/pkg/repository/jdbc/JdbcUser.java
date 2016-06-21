package ru.pkg.repository.jdbc;

import ru.pkg.model.User;

/**
 * Subclass of User that carries temporary restaurant id property which is only relevant for a JDBC implementation
 * of UserRepository.
 */
public class JdbcUser extends User {

    private Integer chosenRestaurantId;

    public Integer getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    public void setChosenRestaurantId(Integer chosenRestaurantId) {
        this.chosenRestaurantId = chosenRestaurantId;
    }
}
