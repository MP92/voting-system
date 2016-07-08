package ru.pkg.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pkg.model.User;
import java.net.URI;
import java.util.Collection;

import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_REST_USER_LIST)
public class AdminRestController extends AbstractUserController {

    @RequestMapping(value = PATH_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable(PATH_VAR_ID) int id) {
        return super.get(id);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable(PATH_VAR_ID) int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        User created = super.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PATH_REST_USER_LIST + PATH_ID).buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(PATH_VAR_ID) int id, @RequestBody User user) {
        super.update(id, user);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findAll() {
        return super.findAll();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testUTF8() {
        return "Текст должно быть видно русскими буквами";
    }
}
