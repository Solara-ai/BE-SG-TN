package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.FeedBacks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedBackRepository extends MongoRepository<FeedBacks, String> {
}
