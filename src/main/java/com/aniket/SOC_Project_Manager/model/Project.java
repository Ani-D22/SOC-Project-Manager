package com.aniket.SOC_Project_Manager.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    //Code Repo
    @NotBlank
    private String repoLink;

    //project status-----------------
    @NotBlank
    private String majorOrMinor;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;
    //---------------------------------

    //Project Report------------------
    @NotBlank
    private String projectReportName;

    @NotBlank
    private String projectReportType;

    @Lob
    @NotBlank
    private byte[] reportFile;
    //---------------------------------

    //Relations------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_no")
    @JsonProperty("enrollment_no")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty("user_id")
    private Mentor mentor;
    //---------------------------------

    public Project(int project_id){}

}