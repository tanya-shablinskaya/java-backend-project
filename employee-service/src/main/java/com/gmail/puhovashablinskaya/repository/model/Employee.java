package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @Column(name = "f_name")
    private String name;

    @Column(name = "f_recruitment_date")
    private LocalDate recruitmentDate;

    @Column(name = "f_termination_date")
    private LocalDate terminationDate;

    @Column(name = "f_iban_byn")
    private String ibanByn;

    @Column(name = "f_iban_currency")
    private String ibanCurrency;

    @Column(name = "f_create_date")
    private LocalDateTime dateOfCreate;

    @Column(name = "f_legal_id")
    private Long legalId;

    @Column(name = "f_position")
    private Boolean position;

    @Column(name = "f_note")
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(name, employee.name)
                && Objects.equals(recruitmentDate, employee.recruitmentDate)
                && Objects.equals(terminationDate, employee.terminationDate)
                && Objects.equals(ibanByn, employee.ibanByn)
                && Objects.equals(ibanCurrency, employee.ibanCurrency)
                && Objects.equals(dateOfCreate, employee.dateOfCreate)
                && Objects.equals(legalId, employee.legalId)
                && Objects.equals(position, employee.position)
                && Objects.equals(note, employee.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recruitmentDate, terminationDate, ibanByn, ibanCurrency, dateOfCreate, legalId, position, note);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recruitmentDate=" + recruitmentDate +
                ", terminationDate=" + terminationDate +
                ", ibanByn='" + ibanByn + '\'' +
                ", ibanCurrency='" + ibanCurrency + '\'' +
                ", dateOfCreate=" + dateOfCreate +
                ", legalId=" + legalId +
                ", position=" + position +
                ", note='" + note + '\'' +
                '}';
    }
}
