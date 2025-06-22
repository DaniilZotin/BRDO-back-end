package com.brdo.demo.services;

import com.brdo.demo.domain.requests.CreateSchoolRequest;
import com.brdo.demo.domain.responses.SchoolResponse;
import com.brdo.demo.enums.SchoolType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SchoolService {
  Page<SchoolResponse> getSchools(String region, SchoolType type, Boolean isActive, Pageable pageable);

  SchoolResponse createSchool(CreateSchoolRequest request);

  SchoolResponse deactivateSchool(UUID id);
}

