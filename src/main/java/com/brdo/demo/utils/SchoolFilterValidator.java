package com.brdo.demo.utils;

import com.brdo.demo.exceptions.FilterSchoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SchoolFilterValidator {

  private static final Pattern REGION_PATTERN = Pattern.compile("^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z\\s\\-']+$");

  // TODO: Later this method can be scaled to support other deactivation parameters or conditions
  public void validate(String region) {
    if (region != null) {
      if (region.length() < 3 || region.length() > 50) {
        throw new FilterSchoolException(MessageUtils.getMessage("REGION_LENGTH_VALIDATION_ERROR"));
      }

      if (!REGION_PATTERN.matcher(region).matches()) {
        throw new FilterSchoolException(MessageUtils.getMessage("REGION_INVALID_CHARACTERS"));
      }
    }
  }
}
