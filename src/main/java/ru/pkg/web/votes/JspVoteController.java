package ru.pkg.web.votes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.model.Votes;

import java.util.List;

@Controller
@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
public class JspVoteController extends AbstractVoteController {

    @RequestMapping("/resetVotes")
    public String resetVotes() {
        super.reset();
        return "redirect:/restaurants";
    }

    @RequestMapping("/vote")
    public String voteForRestaurant(@RequestParam("restaurantId") int restaurantId) {
        super.vote(restaurantId);
        return "redirect:/restaurants?votedId=" + restaurantId;
    }
}
