package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Notifications;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notifications, String> {
}
