package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Schedules;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<Schedules, String> {

    @Query(value = "{ '_id': ?0, 'childSchedules._id': ?1 }",
            fields = "{ 'childSchedules.$': 1 }")
    Optional<Schedules> findChildScheduleById(String scheduleId, String childScheduleId);

    @Query(value = "{ id: ?0, userId: ?1 }")
    Optional<Schedules> findByIdAndUserId(String id, String userId);
}
