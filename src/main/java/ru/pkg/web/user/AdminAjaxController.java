package ru.pkg.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import javax.validation.Valid;
import java.util.Collection;

import static ru.pkg.utils.EntityUtils.createFromTO;
import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_AJAX_USER_LIST)
public class AdminAjaxController extends AbstractUserController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = PATH_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable(PATH_VAR_ID) int id) {
        return super.get(id);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable(PATH_VAR_ID) int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@Valid UserTO user) {
        try {
            if (user.isNew()) {
                super.create(createFromTO(user));
            } else {
                super.update(user);
            }
        } catch (DataIntegrityViolationException e) {
            String message = messageSource.getMessage("exception.user.duplicate_name", null, LocaleContextHolder.getLocale());
            throw new DataIntegrityViolationException(message);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findAll() {
        return super.findAll();
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.POST)
    public void changeEnabledState(@PathVariable(PATH_VAR_ID) int id) {
        super.changeEnabledState(id);
    }
}
