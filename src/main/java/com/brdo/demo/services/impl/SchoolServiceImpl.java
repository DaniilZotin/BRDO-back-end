package com.brdo.demo.services.impl;

import com.brdo.demo.domain.entities.School;
import com.brdo.demo.domain.requests.CreateSchoolRequest;
import com.brdo.demo.domain.responses.SchoolResponse;
import com.brdo.demo.enums.SchoolType;
import com.brdo.demo.exceptions.DuplicateEdrpouException;
import com.brdo.demo.exceptions.SchoolAlreadyActiveException;
import com.brdo.demo.mappers.SchoolMapper;
import com.brdo.demo.repositories.SchoolRepository;
import com.brdo.demo.services.SchoolService;
import com.brdo.demo.utils.MessageUtils;
import com.brdo.demo.utils.SchoolFilterValidator;
import com.brdo.demo.utils.specification_helpers.SchoolSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;
  private final SchoolMapper schoolMapper;
  private final SchoolFilterValidator schoolFilterValidator;

  public Page<SchoolResponse> getSchools(String region, SchoolType type, Boolean isActive, Pageable pageable) {
    schoolFilterValidator.validate(region);

    return schoolRepository.findAll(
        SchoolSpecification.filterBy(region, type, isActive),
        pageable
    ).map(schoolMapper::toResponse);
  }

  public SchoolResponse createSchool(CreateSchoolRequest request) {
    if (schoolRepository.existsByEdrpou(request.edrpou())) {
      throw new DuplicateEdrpouException(
          MessageUtils.getMessage("SCHOOL_DUPLICATE", request.edrpou())
      );
    }

    School school = schoolMapper.toEntity(request);
    School saved = schoolRepository.save(school);
    return schoolMapper.toResponse(saved);
  }

  public SchoolResponse deactivateSchool(UUID id) {
    School school = schoolRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            MessageUtils.getMessage("SCHOOL_NOT_FOUND", id)));

    if (!school.isActive()) {
      throw new SchoolAlreadyActiveException(
          MessageUtils.getMessage("SCHOOL_ALREADY_INACTIVE", id));
    }

    school.setActive(false);
    School saved = schoolRepository.save(school);
    return schoolMapper.toResponse(saved);
  }
}
