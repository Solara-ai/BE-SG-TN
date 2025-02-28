package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
