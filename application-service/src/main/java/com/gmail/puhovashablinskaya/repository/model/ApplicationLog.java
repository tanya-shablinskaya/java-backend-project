package com.gmail.puhovashablinskaya.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_application_log")
@Getter
@Setter
public class ApplicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_status_id_before",
            referencedColumnName = "f_id",
            nullable = false)
    private StatusApplication statusFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_status_id_after",
            referencedColumnName = "f_id",
            nullable = false)
    private StatusApplication statusTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_application_id",
            referencedColumnName = "f_id",
            nullable = false)
    private Application application;

    @Column(name = "f_create_date")
    private LocalDateTime createDate;

    @Column(name = "f_user_id")
    private Long userId;
}
