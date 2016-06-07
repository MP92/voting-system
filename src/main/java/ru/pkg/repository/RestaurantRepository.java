package ru.pkg.repository;

import ru.pkg.model.Restaurant;

import java.util.List;
import java.util.Map;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    List<Restaurant> findAllWithMenu();

    boolean delete(int id);


    Integer findVotesById(int id);

    Map<Integer, Integer> findAllVotes();

    void addVote(int id);

    void resetVotes();

    void addDishToMenu(int id, int dishId);

    void deleteDishFromMenu(int id, int dishId);
}
