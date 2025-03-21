package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TaskManagementRepository extends MongoRepository<TaskManagement, String> {

    @Query("{ 'type': ?0 }")
    List<TaskManagement> findAllByType(String type);
}
