package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.ScheduleException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleExceptionRepository extends MongoRepository<ScheduleException, String> {
}
