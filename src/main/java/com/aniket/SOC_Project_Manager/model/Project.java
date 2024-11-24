package com.aniket.SOC_Project_Manager.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "Project")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    @NotBlank
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_no")
    @JsonProperty("enrollment_no")
    private Student student;

    @NotBlank
    private String description;

    @NotBlank
    private String repoLink;

    @NotBlank
    private String majorOrMinor;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    public Project(int productId){}

}