package com.brdo.demo.domain.entities;

import com.brdo.demo.enums.SchoolType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String edrpou;

  private String region;

  @Enumerated(EnumType.STRING)
  private SchoolType type;

  @Column(name = "is_active")
  private boolean isActive = true;
}
