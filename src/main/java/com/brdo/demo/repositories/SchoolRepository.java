package com.brdo.demo.repositories;

import com.brdo.demo.domain.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID>, JpaSpecificationExecutor<School> {
  boolean existsByEdrpou(String edrpou);
}
