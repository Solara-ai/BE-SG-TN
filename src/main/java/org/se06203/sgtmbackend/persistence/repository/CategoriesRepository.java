package org.se06203.sgtmbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.se06203.sgtmbackend.persistence.entity.Categories;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
