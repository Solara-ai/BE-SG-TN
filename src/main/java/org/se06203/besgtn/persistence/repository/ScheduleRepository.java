package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Schedules;
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
    List<Schedules> findAllByUserId(String userId);

    @Query(value = "{ userId: ?0, 'childSchedules.date': ?1 }"
//            ,fields = "{ 'id': 1, " +
//                    "'userId': 1, " +
//                    "'name': 1, " +
//                    "'description': 1, " +
//                    "'startTime': 1, " +
//                    "'endTime': 1, " +
//                    "'date': 1, " +
//                    "'repeat': 1, " +
//                    "'repeatEndDate': 1, " +
//                    "'remindMe': 1, " +
//                    "'categoryId': 1, " +
//                    "'childSchedules': { $elemMatch: { 'date': ?1 } } }"
                    )
    List<Schedules> findAllByUserIdAndDate(String userId, String date);

    Optional<Schedules> findByChildSchedulesIdAndUserId(String eventId, String userId);
}
