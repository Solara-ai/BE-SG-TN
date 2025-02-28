package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.TaskManagement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskManagementRepository extends MongoRepository<TaskManagement, String> {
}
