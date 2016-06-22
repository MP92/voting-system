package ru.pkg.web.voting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
public class VotingJspController extends AbstractVotingController {

    @RequestMapping("/vote")
    public String voteForRestaurant(@RequestParam("restaurantId") int restaurantId, RedirectAttributes attributes) {
        super.vote(restaurantId);
        attributes.addFlashAttribute("message", "You has voted for restaurant with id=" + restaurantId);
        return "redirect:/restaurants";
    }

    @RequestMapping("/cancelVote")
    public String undoVote() {
        super.cancel();
        return "redirect:/restaurants";
    }

    @RequestMapping("/resetVotes")
    public String resetVotes() {
        super.reset();
        return "redirect:/restaurants";
    }
}
