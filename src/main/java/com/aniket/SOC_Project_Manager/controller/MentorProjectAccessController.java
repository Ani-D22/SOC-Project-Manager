package com.aniket.SOC_Project_Manager.controller;

import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.repo.MentorRepo;
import com.aniket.SOC_Project_Manager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//MENTOR PERSPECTIVE
@RestController
@RequestMapping("/api/mentor/projects")
@RequiredArgsConstructor
public class MentorProjectAccessController {

    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private ProjectService projectService;

    //Show all the projects on app
    @GetMapping
    public ResponseEntity<List<Project>> viewProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    //Show projects submitted to mentor
    @GetMapping("/mentorProjects")
    public ResponseEntity<List<Project>> viewProjects(@AuthenticationPrincipal UserDetails mentorDetails){
        Mentor mentor = mentorRepo.findByEmail(mentorDetails.getUsername());

        return ResponseEntity.ok(projectService.getProjectByMentor(mentor));
    }

    //Show projects by id
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getproject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);

        if(project.getProject_id() > 0) return new ResponseEntity<>(project, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Show approved projects
    @GetMapping("/approvedProjects")
    public ResponseEntity<List<Project>> viewApprovedProjects(@AuthenticationPrincipal UserDetails mentorDetails){
        Mentor mentor = mentorRepo.findByEmail(mentorDetails.getUsername());

        return ResponseEntity.ok(projectService.getApprovedProjectByMentor(mentor));
    }

    //Show pending projects
    @GetMapping("/pendingProjects")
    public ResponseEntity<List<Project>> viewPendingProjects(@AuthenticationPrincipal UserDetails mentorDetails){
        Mentor mentor = mentorRepo.findByEmail(mentorDetails.getUsername());

        return ResponseEntity.ok(projectService.getPendingProjectByMentor(mentor));
    }

    //Show rejected projects
    @GetMapping("/rejectedProjects")
    public ResponseEntity<List<Project>> viewRejectedProjects(@AuthenticationPrincipal UserDetails mentorDetails){
        Mentor mentor = mentorRepo.findByEmail(mentorDetails.getUsername());

        return ResponseEntity.ok(projectService.getRejectedProjectByMentor(mentor));
    }

    //Set status of project
    @PostMapping("/setStatus/{projectId}")
    public String setStatus(@PathVariable Long projectId, @RequestPart String status){

        return projectService.setStatus(projectId, status);
    }
}
