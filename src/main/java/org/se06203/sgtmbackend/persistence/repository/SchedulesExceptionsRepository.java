package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.SchedulesExceptions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesExceptionsRepository extends MongoRepository<SchedulesExceptions, String> {
}
