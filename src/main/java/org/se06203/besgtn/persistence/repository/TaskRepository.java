package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Tasks;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Tasks, String> {

    @Query(value = "{ 'status' : { $ne: ?0 } }", count = true)
    long countByStatusNot(Constants.TypeTask status);

    @Query("{ 'epicId': ?0 }")
    List<Tasks> findAllByEpicId(String epicId);
}

