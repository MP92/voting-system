package ru.pkg.web.votes;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.model.Votes;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/restaurants")
public class VoteRestController extends AbstractVoteController {

    @RequestMapping(path = "/{restaurantId}/vote", method = RequestMethod.POST)
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        super.vote(restaurantId);
    }

    @RequestMapping(path = "/{restaurantId}/votes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Votes findById(@PathVariable("restaurantId") int restaurantId) {
        return super.findById(restaurantId);
    }

    @RequestMapping(path = "/votes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Votes> findAll() {
        return super.findAll();
    }

    @RequestMapping(path = "/{restaurantId}/votes/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Integer findCount(int restaurantId) {
        return super.findCount(restaurantId);
    }

    @RequestMapping(path = "/{restaurantId}/votes/reset", method = RequestMethod.PUT)
    public void reset() {
        super.reset();
    }

    @RequestMapping(path = "/{restaurantId}/unvote", method = RequestMethod.POST)
    public void unvote(@PathVariable("restaurantId") int restaurantId) {
        super.unvote(restaurantId);
    }
}
