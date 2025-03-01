package org.se06203.sgtmbackend.persistence.repository;

import org.se06203.sgtmbackend.persistence.entity.Messages;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends MongoRepository<Messages, String> {
}
