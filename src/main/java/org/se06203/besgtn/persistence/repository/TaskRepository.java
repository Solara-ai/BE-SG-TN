package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Tasks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Tasks, String> {

    @Query(value = "{ 'status' : ?0 }", count = true)
    long countByStatus(String status);

    Tasks findAllByEpicId(String epicId);
//    Tasks countByStatus(String status);
}

