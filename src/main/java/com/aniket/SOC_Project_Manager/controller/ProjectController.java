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


//STUDENT PERSPECTIVE
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

    //Show all projects from student
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(@AuthenticationPrincipal UserDetails studentDetails) {
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        return ResponseEntity.ok(projectService.getProjectByStudent(student));
    }

    //Show Project by id
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getproject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);

        if(project.getProject_id() > 0) return new ResponseEntity<>(project, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Add new Project
    @PostMapping("/addProject")
    public ResponseEntity<?> createProject(@RequestBody Project project, @AuthenticationPrincipal UserDetails studentDetails) throws IOException {
        Project savedProject = null;
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        savedProject = projectService.addProject(project, student);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    //Update Project
    @PutMapping("/updateProject/{projectId}")
    public ResponseEntity<?> updateProject(@RequestBody Project project, @PathVariable Long projectId,
                                           @AuthenticationPrincipal UserDetails studentDetails) throws IOException {
        Project savedProject = null;
        Student student = studentRepo.findByEmail(studentDetails.getUsername());

        savedProject = projectService.updateProject(project, projectId, student);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    //Delete Project
    @DeleteMapping("/deleteProject/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId,
                                           @AuthenticationPrincipal UserDetails studentDetails) {
        Student student = studentRepo.findByEmail(studentDetails.getUsername());
        Project project = projectService.getProject(projectId);
        if(project != null){
            projectService.deleteProject(projectId, student);
            System.out.println("Deleted");
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else{
            System.out.println("Deletion Failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
