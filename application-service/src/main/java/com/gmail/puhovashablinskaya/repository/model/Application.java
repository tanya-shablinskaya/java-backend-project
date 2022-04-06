package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_application")
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @Column(name = "f_application_id")
    private String applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_currency_from_id",
            referencedColumnName = "f_id",
            nullable = false)
    private Currency currencyFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_currency_to_id",
            referencedColumnName = "f_id",
            nullable = false)
    private Currency currencyTo;

    @Column(name = "f_employee_id")
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_status_id",
            referencedColumnName = "f_id",
            nullable = false)
    private StatusApplication status;

    @Column(name = "f_percent")
    private Float percent;

    @Column(name = "f_create_date")
    private LocalDateTime createDate;

    @Column(name = "f_last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "f_legal_id")
    private Long legalId;

    @Column(name = "f_note")
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) && Objects.equals(applicationId, that.applicationId) && Objects.equals(currencyFrom, that.currencyFrom) && Objects.equals(currencyTo, that.currencyTo) && Objects.equals(employeeId, that.employeeId) && Objects.equals(status, that.status) && Objects.equals(percent, that.percent) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(legalId, that.legalId) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationId, currencyFrom, currencyTo, employeeId, status, percent, createDate, lastUpdateDate, legalId, note);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicationId='" + applicationId + '\'' +
                ", currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                ", employeeId=" + employeeId +
                ", status=" + status +
                ", percent=" + percent +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", legalId=" + legalId +
                ", note='" + note + '\'' +
                '}';
    }
}
