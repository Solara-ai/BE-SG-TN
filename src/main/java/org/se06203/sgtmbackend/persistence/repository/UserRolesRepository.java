package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.UserRoles;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
    @Query(value = "SELECT * FROM user_role ur WHERE ur.user_id = :userId AND ur.role = :role", nativeQuery = true)
    Optional<UserRoles> findAllByUserId(Constants.role role  , Long userId);

}
