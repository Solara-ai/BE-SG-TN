package org.se06203.sgtmbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String description;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private boolean isException;
    private String repeat;
    private LocalDateTime repeatEndDate;
    private boolean remindMe;

    @ManyToOne
    @JoinColumn(name = "parent_schedule_id")
    private Schedules parentSchedule;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;
}
