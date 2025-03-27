package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Plans;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanRepository extends MongoRepository<Plans, String> {
    long countByStatus(Constants.Status status);
}
