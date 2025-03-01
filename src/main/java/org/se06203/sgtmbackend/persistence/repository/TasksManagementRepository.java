package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Tasks;
import org.se06203.sgtmbackend.persistence.entity.TasksManagement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksManagementRepository extends MongoRepository<TasksManagement, String> {
}
