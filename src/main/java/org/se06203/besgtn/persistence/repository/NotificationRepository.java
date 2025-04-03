package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Notifications;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notifications, String> {

    Notifications findByUserIdAndStatus(List<String> userId, Constants.StatusNotification status);
//
//    @Query(value = "{ userId: ?0, status: ?1 }")
//    Notifications findByUserIdAndStatus(String userId, String status);
}
