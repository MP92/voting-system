package ru.pkg.web.votes;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.to.VotingStatistics;

import java.util.List;

@RestController
@RequestMapping("/rest/restaurants")
public class VoteRestController extends AbstractVoteController {

    @RequestMapping(path = "/{restaurantId}/vote", method = RequestMethod.POST)
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        super.vote(restaurantId);
    }

    @RequestMapping(path = "/votes/cancel", method = RequestMethod.POST)
    public void cancelVote() {
        super.cancelVote();
    }

    @RequestMapping(path = "/votes/reset", method = RequestMethod.PUT)
    public void reset() {
        super.reset();
    }

    @RequestMapping(path = "/votes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<VotingStatistics> getVotingStatistics() {
        return super.getVotingStatistics();
    }
}
