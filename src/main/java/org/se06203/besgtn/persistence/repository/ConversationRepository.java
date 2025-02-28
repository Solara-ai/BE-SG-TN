package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Conversations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversations, String> {
}
