package ru.pkg.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = @UniqueConstraint(name = "restaurant_unique_name_idx", columnNames = "name"))
public class Restaurant extends NamedEntity {

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @Column(name = "address", nullable = false)
    @NotEmpty
    private String address;

    @Column(name = "phone_number", nullable = false)
    @NotEmpty
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Where(clause = "in_menu = true")
    @OrderBy("id")
    @JsonManagedReference
    private List<Dish> menu = Collections.emptyList();

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<UserVote> userVotes;

    public Restaurant() {
    }

    public Restaurant(Integer id) {
        super(id, null);
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber) {
        this(id, name, description, address, phoneNumber, null);
    }

    public Restaurant(Integer id, String name, String description, String address, String phoneNumber, List<Dish> menu) {
        super(id, name);
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        if (menu != null) {
            this.menu = new ArrayList<>(menu);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Dish> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu != null ? new ArrayList<>(menu) : Collections.emptyList();
    }

    public void addDishToMenu(Dish dish) {
        menu.add(dish);
    }

    public List<UserVote> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(List<UserVote> userVotes) {
        this.userVotes = userVotes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
