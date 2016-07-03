package ru.pkg.web.voting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.to.VotingStatistics;
import ru.pkg.web.restaurant.RestaurantAjaxController;

import java.util.List;

@RestController
@RequestMapping(VotingAjaxController.AJAX_URL)
public class VotingAjaxController extends AbstractVotingController {

    public static final String AJAX_URL = RestaurantAjaxController.AJAX_URL;

    @RequestMapping(path = "/{restaurantId}/vote", method = RequestMethod.POST)
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        super.vote(restaurantId);
    }

    @RequestMapping(path = "/voting/cancel", method = RequestMethod.POST)
    public void cancel() {
        super.cancel();
    }

    @RequestMapping(path = "/voting/reset", method = RequestMethod.POST)
    public void reset() {
        super.reset();
    }

    @RequestMapping(path = "/voting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotingStatistics> findVotingStatistics() {
        return super.findVotingStatistics();
    }
}
