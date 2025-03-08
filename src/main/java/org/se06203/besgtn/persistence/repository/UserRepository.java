package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<Users, String> {

    Optional<Users> findByIdAndRoles(String userId, String role);

    Optional<Users> findByEmail(String email);

    @Query("""
            { email : ?0, roles : ?1 }
            """)
    Optional<Users> findByEmailAndRoleIn(String email, String role);
}
