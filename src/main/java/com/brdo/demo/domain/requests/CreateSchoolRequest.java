package com.brdo.demo.domain.requests;

import com.brdo.demo.enums.SchoolType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateSchoolRequest(

    @NotBlank(message = "School name must not be blank")
    @Size(min = 4, max = 100, message = "School name must be between 4 and 100 characters")
    @Pattern(
        regexp = "^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z0-9\\s\\-№']+$",
        message = "School name contains invalid characters"
    )
    String name,

    @NotBlank(message = "edrpou must not be blank")
    @Pattern(regexp = "\\d{8}", message = "edrpou minimum must has 8 numbers")
    String edrpou,

    @NotBlank(message = "Region must not be blank")
    @Size(min = 4, max = 50, message = "Region must be between 4 and 50 characters")
    @Pattern(
        regexp = "^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z\\s\\-']+$",
        message = "Region contains invalid characters"
    )
    String region,

    @NotNull(message = "type must be not null")
    SchoolType type
) {}
