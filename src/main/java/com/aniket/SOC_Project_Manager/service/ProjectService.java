package com.aniket.SOC_Project_Manager.service;


import com.aniket.SOC_Project_Manager.model.ApprovalStatus;
import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.repo.ProjectRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepo projectRepo;

    public List<Project> getProjectByStudent(Student student) {
        return projectRepo.findByStudent(student);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Project getProject(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project addProject(Project project, Student student) {
        project.setStatus(ApprovalStatus.PENDING);
        project.setStudent(student);
        return projectRepo.save(project);
    }
    public Project updateProject(Project project, Long projectId, Student student) {
        projectRepo.deleteById(projectId);
        project.setStatus(ApprovalStatus.PENDING);
        project.setStudent(student);
        return projectRepo.save(project);
    }

    public void deleteProject(Long id, Student student) {
        if(projectRepo.findByStudent(student)==student){
            projectRepo.deleteById(id);
        }
        else System.out.println("Student enrollment_no does not match");
    }
}