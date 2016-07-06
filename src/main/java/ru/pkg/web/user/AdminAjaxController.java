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
import ru.pkg.web.exception.ErrorInfoExceptionHandler;

import javax.validation.Valid;
import java.util.Collection;

import static ru.pkg.utils.EntityUtils.createFromTO;

@RestController
@RequestMapping(AdminAjaxController.AJAX_URL)
public class AdminAjaxController extends AbstractUserController implements ErrorInfoExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public static final String AJAX_URL = "/ajax/users";

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
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
            throw new DataIntegrityViolationException(messageSource.getMessage("exception.user.duplicate_name", null, LocaleContextHolder.getLocale()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findAll() {
        return super.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public void changeEnabledState(@PathVariable("id") int id) {
        super.changeEnabledState(id);
    }
}
