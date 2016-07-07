package ru.pkg.web.voting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.to.VotingStatistics;
import java.util.List;

import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_REST_RESTAURANT_LIST)
public class VotingRestController extends AbstractVotingController {

    @RequestMapping(value = PATH_VOTE, method = RequestMethod.POST)
    public void vote(@PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        super.vote(restaurantId);
    }

    @RequestMapping(value = PATH_VOTE_CANCEL, method = RequestMethod.POST)
    public void cancel() {
        super.cancel();
    }

    @RequestMapping(value = PATH_VOTE_RESET, method = RequestMethod.PUT)
    public void reset() {
        super.reset();
    }

    @RequestMapping(value = PATH_VOTING_STATISTICS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotingStatistics> findVotingStatistics() {
        return super.findVotingStatistics();
    }
}
