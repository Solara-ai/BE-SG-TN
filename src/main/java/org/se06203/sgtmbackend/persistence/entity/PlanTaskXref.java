package org.se06203.sgtmbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plan_task_xref")
public class PlanTaskXref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plans plan;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Tasks task;
}

