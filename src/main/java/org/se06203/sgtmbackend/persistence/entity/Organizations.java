package org.se06203.sgtmbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "organization")
public class Organizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Users admin;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "organization")
    private List<UserOrganizationXref> userOrganizations;
}
