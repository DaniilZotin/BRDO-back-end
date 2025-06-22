package com.brdo.demo.controllers;

import com.brdo.demo.domain.requests.CreateSchoolRequest;
import com.brdo.demo.domain.responses.SchoolResponse;
import com.brdo.demo.enums.SchoolType;
import com.brdo.demo.services.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
@Tag(
    name        = "Schools",
    description = "Endpoints for management schools records"
)
public class SchoolController {

  private final SchoolService schoolService;

  @Operation(
      summary = "Get a paginated list of schools",
      description = "Returns a filtered list of schools by region, type, and activity status",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved list of schools"),
          @ApiResponse(responseCode = "400", description = "Invalid filter parameters", content = @Content),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )
  @GetMapping
  public ResponseEntity<Page<SchoolResponse>> getSchools(
      @Parameter(description = "Region name (max 50 characters)", example = "Київська")
      @RequestParam(required = false) String region,

      @Parameter(description = "Type of school", example = "LYCEUM", schema = @Schema(implementation = SchoolType.class))
      @RequestParam(required = false) SchoolType type,

      @Parameter(description = "Activity status (true/false)", example = "true")
      @RequestParam(required = false) Boolean isActive,

      @ParameterObject Pageable pageable
  ) {
    return ResponseEntity.ok(schoolService.getSchools(region, type, isActive, pageable));
  }


  @Operation(
      summary = "Create a new school",
      description = "Creates and returns a new school record",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully created school"),
          @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
      }
  )

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<SchoolResponse> createSchool(
      @Valid @RequestBody CreateSchoolRequest request
  ) {
    return ResponseEntity.ok(schoolService.createSchool(request));
  }

  @Operation(
      summary = "Deactivate a school by ID",
      description = "Sets a school's active status to false",
      responses = {
          @ApiResponse(responseCode = "200", description = "School successfully deactivated"),
          @ApiResponse(responseCode = "404", description = "School not found", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
      }
  )
  @PatchMapping("/{id}/deactivate")
  public ResponseEntity<SchoolResponse> deactivateSchool(@PathVariable UUID id) {
    return ResponseEntity.ok(schoolService.deactivateSchool(id));
  }
}