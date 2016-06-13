package ru.pkg.service;

import ru.pkg.model.Votes;

import java.util.List;

public interface VotesService {

    void vote(int userId, int restaurantId);

    void unvote(int userId, int restaurantId);

    Votes findById(int restaurantId);

    List<Votes> findAll();

    Integer findCount(int restaurantId);

    void reset();
}
