package ru.pkg.to;

import org.hibernate.validator.constraints.NotEmpty;
import ru.pkg.utils.TimeUtil;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.pkg.utils.constants.EntityConstraints.*;

public class UserTO {

    private Integer id;

    @NotEmpty
    @Size(min = NAME_MIN, max = NAME_MAX)
    private String name;

    @NotEmpty
    @Size(min = NAME_MIN, max = NAME_MAX)
    private String surname;

    @NotEmpty
    @Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
    private String password;

    private LocalDateTime lastVoted;

    public UserTO() {
    }

    public UserTO(Integer id, String name, String surname, String password, LocalDateTime lastVoted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.lastVoted = lastVoted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(LocalDateTime lastVoted) {
        this.lastVoted = lastVoted;
    }

    public boolean isVotedToday() {
        return TimeUtil.isToday(lastVoted);
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
