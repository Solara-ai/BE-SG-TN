package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);
}
