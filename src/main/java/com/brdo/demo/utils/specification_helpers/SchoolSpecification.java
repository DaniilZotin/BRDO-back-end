package com.brdo.demo.utils.specification_helpers;

import com.brdo.demo.domain.entities.School;
import com.brdo.demo.enums.SchoolType;
import org.springframework.data.jpa.domain.Specification;

public class SchoolSpecification {

  public static Specification<School> filterBy(String region,
                                               SchoolType type,
                                               Boolean isActive) {

    String regionTrim = region == null ? null : region.trim();

    return Specification
        .where(regionTrim != null && regionTrim.length() >= 4
            ? byRegion(regionTrim)
            : null)
        .and(type      != null ? byType(type)         : null)
        .and(isActive  != null ? byIsActive(isActive) : null);
  }

  private static Specification<School> byRegion(String region) {
    return (root, query, cb) ->
        cb.like(cb.lower(root.get("region")),
            "%" + region.toLowerCase() + "%");
  }

  private static Specification<School> byType(SchoolType type) {
    return (root, query, cb) -> cb.equal(root.get("type"), type);
  }

  private static Specification<School> byIsActive(Boolean isActive) {
    return (root, query, cb) -> cb.equal(root.get("isActive"), isActive);
  }
}

