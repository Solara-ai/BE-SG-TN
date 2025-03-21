package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskManagementRepository extends MongoRepository<TaskManagement, String> {

    @Query("{ 'deletedAt': null , 'userId': ?0 }")
    List<TaskManagement> findAllActiveEpicsByUserId(String userId);

    @Query("{ 'type': ?0 , 'userId': ?1, 'deletedAt': null }")
    List<TaskManagement> findAllByTypeAndUserId(String type, String userId);

    @Query("{ 'id': ?0 , 'deletedAt': null, 'userId': ?1 }")
    Optional<TaskManagement> findActiveEpicByIdAndUserId(String id, String userId);

    @Query("{ 'id': ?0 , 'deletedAt': null }")
    Optional<TaskManagement> findActiveEpicById(String id);

    @Query("{ 'type': ?0 }")
    List<TaskManagement> findAllByType(String type);
}
