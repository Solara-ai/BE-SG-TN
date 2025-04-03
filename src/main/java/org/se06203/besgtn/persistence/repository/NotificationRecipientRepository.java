package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.NotificationRecipient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRecipientRepository extends MongoRepository<NotificationRecipient, String> {




}
