package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Organizations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends MongoRepository<Organizations, String> {
}

