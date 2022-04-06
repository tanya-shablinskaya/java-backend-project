package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "t_legal")
@Getter
@Setter
public class Legal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @Column(name = "f_name")
    private String name;

    @Column(name = "f_unp")
    private String unp;

    @Column(name = "f_iban")
    private String iban;

    @Column(name = "f_residence")
    private Boolean residence;

    @Column(name = "f_create_date")
    private LocalDate dateOfCreate;

    @Column(name = "f_last_update_date")
    private LocalDate dateOfLastUpdate;

    @Column(name = "f_employee_count")
    private Integer employeeCount;

    @Column(name = "f_note")
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Legal legal = (Legal) o;
        return Objects.equals(id, legal.id)
                && Objects.equals(name, legal.name)
                && Objects.equals(unp, legal.unp)
                && Objects.equals(iban, legal.iban)
                && Objects.equals(residence, legal.residence)
                && Objects.equals(dateOfCreate, legal.dateOfCreate)
                && Objects.equals(dateOfLastUpdate, legal.dateOfLastUpdate)
                && Objects.equals(note, legal.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unp, iban, residence, dateOfCreate, dateOfLastUpdate, note);
    }

    @Override
    public String toString() {
        return "Legal{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", unp=" + unp +
                ", iban='" + iban + '\'' +
                ", residence=" + residence +
                ", dateOfCreate=" + dateOfCreate +
                ", dateOfLastUpdate=" + dateOfLastUpdate +
                ", note='" + note + '\'' +
                '}';
    }
}
