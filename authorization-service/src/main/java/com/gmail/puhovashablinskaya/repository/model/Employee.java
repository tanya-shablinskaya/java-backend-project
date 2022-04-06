package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;

    @Column(name = "f_login")
    private String username;

    @Column(name = "f_password")
    private String password;

    @Column(name = "f_email")
    private String usermail;

    @Column(name = "f_name", unique = true)
    private String firstName;

    @Column(name = "f_create_date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_status_id",
            referencedColumnName = "f_id",
            nullable = false)
    private Status status;

    @Column(name = "f_failed_attempt")
    private int failedAttempt;

    @Column(name = "f_locked_account")
    private boolean lockedAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return lockedAccount == employee.lockedAccount
                && Objects.equals(id, employee.id)
                && Objects.equals(username, employee.username)
                && Objects.equals(password, employee.password)
                && Objects.equals(usermail, employee.usermail)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(date, employee.date)
                && Objects.equals(status, employee.status)
                && Objects.equals(failedAttempt, employee.failedAttempt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, usermail, firstName, date, status, failedAttempt, lockedAccount);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", usermail='" + usermail + '\'' +
                ", firstName='" + firstName + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
