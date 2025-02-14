package org.se06203.sgtmbackend.persistence.repository;

import  org.se06203.sgtmbackend.persistence.entity.UserOrganizationXref;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrganizationXrefRepository extends JpaRepository<UserOrganizationXref, Long> {
}
