package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Users;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);

    @Query(value = "SELECT u.* FROM users u INNER JOIN user_role ur ON u.id = ur.user_id WHERE u.email = :email AND ur.role = :role", nativeQuery = true)
    Optional<Users> findByEmailAndRole(String email, Constants.role role);



}
