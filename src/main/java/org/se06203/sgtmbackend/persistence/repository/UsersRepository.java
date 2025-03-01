package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Users;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users,String> {

    Optional<Users> findByUserName(String userName);

    @Query("{ 'email': ?0, 'role': ?1 }")
    Optional<Users> findByEmailAndRole(String email, Constants.role role);

//    Object findAllByUserId(Constants.role role, String id);
    @Query("{ '_id': ?0, 'role': ?1 }")
    Optional<Object> findByIdAndRoles(Object userId, Object role);
}
