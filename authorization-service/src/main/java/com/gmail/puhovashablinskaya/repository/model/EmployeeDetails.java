package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_authorization_log")
@Getter
@Setter
public class EmployeeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;

    @Column(name = "f_username")
    private String username;

    @Column(name = "f_date_of_auth")
    private LocalDateTime dateOfAuth;

    @Column(name = "f_date_of_logout")
    private LocalDateTime dateOfLogout;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails that = (EmployeeDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(dateOfAuth, that.dateOfAuth) && Objects.equals(dateOfLogout, that.dateOfLogout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, dateOfAuth, dateOfLogout);
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", dateOfAuth=" + dateOfAuth +
                ", dateOfLogout=" + dateOfLogout +
                '}';
    }
}
