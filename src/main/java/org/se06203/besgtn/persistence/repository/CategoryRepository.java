package org.se06203.besgtn.persistence.repository;

import org.se06203.besgtn.persistence.entity.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Categories, String> {

    List<Categories> findAllByUserId(String userId);
}
