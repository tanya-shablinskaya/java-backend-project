package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_session")
@Getter
@Setter
public class Session {
    @Id
    @Column(name = "f_id_session", unique = true)
    private String idSession;

    @Column(name = "f_username")
    private String username;

    @Column(name = "f_date_of_start")
    private LocalDateTime dateOfStart;

    @Column(name = "f_date_of_finish")
    private LocalDateTime dateOfFinish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_status_id",
            referencedColumnName = "f_id")
    private StatusSession statusSession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(idSession, session.idSession) && Objects.equals(username, session.username) && Objects.equals(dateOfStart, session.dateOfStart) && Objects.equals(dateOfFinish, session.dateOfFinish) && Objects.equals(statusSession, session.statusSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSession, username, dateOfStart, dateOfFinish, statusSession);
    }

    @Override
    public String
    toString() {
        return "Session{" +
                "idSession='" + idSession + '\'' +
                ", username='" + username + '\'' +
                ", dateOfStart=" + dateOfStart +
                ", dateOfFinish=" + dateOfFinish +
                ", statusSession=" + statusSession +
                '}';
    }
}
