package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.dto.response.ViewSchedulesEventResponse;
import org.se06203.besgtn.persistence.entity.Schedules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<Schedules, String> {

    @Query(value = "{ '_id': ?0, 'childSchedules._id': ?1 }",
            fields = "{ 'childSchedules.$': 1 }")
    Optional<Schedules> findChildScheduleById(String scheduleId, String childScheduleId);

    @Query(value = "{ id: ?0, userId: ?1 }")
    Optional<Schedules> findByIdAndUserId(String id, String userId);

    @Query(value = "{ userId: ?0 }")
    Page<Schedules> findAllByUserId(String userId, Pageable pageable);

    @Query("{ 'userId': ?0, 'childSchedules.date': { $gte: ?1, $lte: ?2 } }")
    List<Schedules> findAllByUserIdAndChildDateBetween(String userId, String startDate, String endDate);

    @Query(value = "{ userId: ?0, 'childSchedules.date': ?1 }")
    List<Schedules> findAllByUserIdAndDate(String userId, String date);

    Optional<Schedules> findByChildSchedulesIdAndUserId(String eventId, String userId);

    Optional<Schedules> findByDate(String date);
}
