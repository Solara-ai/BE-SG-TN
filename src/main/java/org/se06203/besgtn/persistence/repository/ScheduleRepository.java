package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Schedules;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<Schedules, String> {

    @Query(value = "{ id: ?0, userId: ?1 }")
    Optional<Schedules> findByIdAndUserId(String id, String userId);

    @Query("{ 'userId': ?0, 'childSchedules.date': { $gte: ?1, $lte: ?2 } }")
    List<Schedules> findAllByUserIdAndChildDateBetween(String userId, String startDate, String endDate);

    @Query(value = "{ userId: ?0, 'childSchedules.date': ?1 }")
    List<Schedules> findAllByUserIdAndDate(String userId, String date);

    @Query(value = "{ userId: ?0, 'childSchedules.date': ?1, 'startTime': ?2, 'endTime': ?3 }")
    Optional<Schedules> findByUserIdAndChildSchedulesDateAndStartTimeAndEndTime(String userId,
                                                                               String date,
                                                                               String startTime,
                                                                               String endTime);

    Optional<Schedules> findByChildSchedulesIdAndUserId(String eventId, String userId);

    List<Schedules> findAllByUserId(String userId);
}
