package com.brdo.demo.configurations;

import com.brdo.demo.exceptions.DuplicateEdrpouException;
import com.brdo.demo.exceptions.FilterSchoolException;
import com.brdo.demo.exceptions.SchoolAlreadyActiveException;
import com.brdo.demo.utils.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private static final String ERROR_MESSAGE = "GlobalExceptionHandler: ";

  @ExceptionHandler(FilterSchoolException.class)
  public ProblemDetail handleSchoolsGetParametersException(FilterSchoolException ex) {
    log.error(ERROR_MESSAGE, ex);
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(DuplicateEdrpouException.class)
  public ProblemDetail handleDuplicateEdrpou(DuplicateEdrpouException ex) {
    log.error(ERROR_MESSAGE, ex);
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
    log.error(ERROR_MESSAGE, ex);

    String detail = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage()))
        .collect(Collectors.joining("; "));

    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Bad Request");
    problemDetail.setDetail(detail);
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ProblemDetail handleEnumValuesException(MethodArgumentTypeMismatchException ex) {
    log.info(ERROR_MESSAGE, ex);
    String paramName = ex.getName();
    String invalidValue = String.valueOf(ex.getValue());
    String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";

    String message = MessageUtils.getMessage(
        "ENUM_SCHOOL_ERROR",
        invalidValue,
        paramName,
        requiredType,
        getEnumValuesAsString(ex.getRequiredType())
    );

    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ProblemDetail handleNotFound(NoHandlerFoundException ex) {
    log.info(ERROR_MESSAGE, ex);
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Endpoint '%s' not found", ex.getRequestURL()));
  }

  @ExceptionHandler(SchoolAlreadyActiveException.class)
  public ProblemDetail handleAlreadyActive(SchoolAlreadyActiveException ex) {
    log.error(ERROR_MESSAGE, ex);
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
  }

  private String getEnumValuesAsString(Class<?> enumType) {
    if (enumType != null && enumType.isEnum()) {
      Object[] constants = enumType.getEnumConstants();
      return Arrays.stream(constants)
          .map(Object::toString)
          .collect(Collectors.joining(", "));
    }
    return "unknown";
  }
}
