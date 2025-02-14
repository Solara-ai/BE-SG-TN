package org.se06203.sgtmbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules_user_xref")

public class SchedulesUserXref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedules schedule;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
