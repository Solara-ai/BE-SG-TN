package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Messages, String> {
}
