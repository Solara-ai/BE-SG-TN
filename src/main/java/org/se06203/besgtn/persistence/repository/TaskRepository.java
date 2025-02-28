package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Tasks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Tasks, String> {
}

