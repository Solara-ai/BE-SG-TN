package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Tasks;
import org.se06203.sgtmbackend.persistence.entity.TasksManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksManagementRepository extends JpaRepository<TasksManagement, Long> {
}
