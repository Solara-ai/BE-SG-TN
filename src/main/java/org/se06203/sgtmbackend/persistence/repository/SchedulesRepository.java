package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Schedules;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends MongoRepository<Schedules,String> {
}
