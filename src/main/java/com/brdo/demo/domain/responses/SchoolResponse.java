package com.brdo.demo.domain.responses;

import com.brdo.demo.enums.SchoolType;

import java.util.UUID;

public record SchoolResponse(
    UUID id,
    String name,
    String edrpou,
    String region,
    SchoolType type,
    boolean isActive
) {}
