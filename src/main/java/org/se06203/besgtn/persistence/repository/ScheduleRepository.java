package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Schedules;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedules, String> {
}
