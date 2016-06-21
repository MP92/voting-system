package ru.pkg.web.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.web.restaurant.RestaurantRestController;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController extends AbstractUserController {

    public static final String REST_URL = RestaurantRestController.REST_URL;

    @RequestMapping(path = "/{restaurantId}/vote", method = RequestMethod.POST)
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        super.vote(restaurantId);
    }

    @RequestMapping(path = "/votes/cancel", method = RequestMethod.POST)
    public void cancelVote() {
        super.cancelVote();
    }
}
