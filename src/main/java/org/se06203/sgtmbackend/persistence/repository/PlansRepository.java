package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Plans;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlansRepository extends MongoRepository<Plans,String> {
}
