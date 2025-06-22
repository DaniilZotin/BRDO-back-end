package com.brdo.demo.mappers;

import com.brdo.demo.domain.entities.School;
import com.brdo.demo.domain.requests.CreateSchoolRequest;
import com.brdo.demo.domain.responses.SchoolResponse;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

  public School toEntity(CreateSchoolRequest request) {
    return School.builder()
        .name(request.name())
        .edrpou(request.edrpou())
        .region(request.region())
        .type(request.type())
        .isActive(true)
        .build();
  }

  public SchoolResponse toResponse(School school) {
    return new SchoolResponse(
        school.getId(),
        school.getName(),
        school.getEdrpou(),
        school.getRegion(),
        school.getType(),
        school.isActive()
    );
  }
}
