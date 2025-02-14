package org.se06203.sgtmbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.apache.bcel.classfile.Constant;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.security.core.userdetails.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_role")

public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(length = 10, nullable = false)
    private Constants.role role;
}
