package org.se06203.sgtmbackend.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class Users extends AbstractAuditingEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "first_name",length = 100,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 100,nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 10)
    private String phone;

    @Column(length = 10)
    private String gender;

    @OneToMany(mappedBy = "user")
    private List<UserRoles> roles;

    @OneToMany(mappedBy = "admin")
    private List<Organizations> organizations;
}
