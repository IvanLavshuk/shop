package market.retail.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    public User(Integer id) {
        this.id = id;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;
    @Column(name = "email", nullable = false, length = 45)
    private String email;
    @Column(name = "password", nullable = false, length = 45)
    private char[] password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name) && surname.equals(user.surname) && email.equals(user.email) &&
                Arrays.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, surname, email);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    public void setPassword(char[] password) {
        this.password = Arrays.copyOf(password, password.length);
    }

    public void clearPassword() {
        if (password != null) {
            Arrays.fill(password, '\0');
        }
    }
}
