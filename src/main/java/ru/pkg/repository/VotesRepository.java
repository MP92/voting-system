package ru.pkg.repository;

import ru.pkg.model.Votes;

import java.util.List;

public interface VotesRepository {

    void up(int restaurantId);

    void down(int restaurantId);

    Votes findById(int restaurantId);

    List<Votes> findAll();

    Integer findCount(int restaurantId);

    void reset();
}
