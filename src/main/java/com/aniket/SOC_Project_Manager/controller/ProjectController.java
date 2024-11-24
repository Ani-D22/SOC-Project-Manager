package com.aniket.SOC_Project_Manager.controller;


import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.repo.ProjectRepo;
import com.aniket.SOC_Project_Manager.repo.StudentRepo;
import com.aniket.SOC_Project_Manager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/student/projects")
@RequiredArgsConstructor
class ProjectController {
    @Autowired
    private final ProjectService projectService;
    @Autowired
    public final ProjectRepo projectRepo;
    @Autowired
    public final StudentRepo studentRepo;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(@AuthenticationPrincipal UserDetails studentDetails) {
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        return ResponseEntity.ok(projectService.getProjectByStudent(student));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getproject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);

        if(project.getProject_id() > 0) return new ResponseEntity<>(project, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addProject")
    public ResponseEntity<?> createProject(@RequestBody Project project, @AuthenticationPrincipal UserDetails studentDetails) throws IOException {
        Project savedProject = null;
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        savedProject = projectService.addProject(project, student);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping("/updateProject/{projectId}")
    public ResponseEntity<?> updateProject(@RequestBody Project project, @PathVariable Long projectId,
                                           @AuthenticationPrincipal UserDetails studentDetails) throws IOException {
        Project savedProject = null;
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        savedProject = projectService.updateProject(project, projectId, student);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProject/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        if(project != null){
            projectService.deleteProject(projectId);
            System.out.println("Deleted");
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else{
            System.out.println("Deletion Failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
