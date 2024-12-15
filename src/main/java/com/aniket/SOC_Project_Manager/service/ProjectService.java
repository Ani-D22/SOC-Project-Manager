package com.aniket.SOC_Project_Manager.service;


import com.aniket.SOC_Project_Manager.model.ApprovalStatus;
import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.repo.ProjectRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectRepo projectRepo;

//Student Access Controller-Service-------------------------------------------------------------------------------------

    public List<Project> getProjectByStudent(Student student) {
        return projectRepo.findByStudent(student);
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
        Optional<Project> project = projectRepo.findById(id);
        if(project.get().getStudent().getEnrollment_no().equals(student.getEnrollment_no())){
            projectRepo.deleteById(id);
        }
        else System.out.println("Student enrollment_no does not match");
    }

//---------------------------------------------------------------------------------------------------------------------
//Mentor Access Controller-Service-------------------------------------------------------------------------------------

    public List<Project> getProjectByMentor(Mentor mentor) {
        return projectRepo.findByMentor(mentor);
    }

    public List<Project> getApprovedProjectByMentor(Mentor mentor) {
        List<Project> list = projectRepo.findByMentor(mentor);
        for (Project i : list) {
            if (!i.getStatus().equals(ApprovalStatus.APPROVED)){
                list.remove(i);
            }
        }
        return list;
    }

    public List<Project> getPendingProjectByMentor(Mentor mentor) {
        List<Project> list = projectRepo.findByMentor(mentor);
        for (Project i : list) {
            if (!i.getStatus().equals(ApprovalStatus.PENDING)){
                list.remove(i);
            }
        }
        return list;
    }

    public List<Project> getRejectedProjectByMentor(Mentor mentor) {
        List<Project> list = projectRepo.findByMentor(mentor);
        for (Project i : list) {
            if (!i.getStatus().equals(ApprovalStatus.REJECTED)){
                list.remove(i);
            }
        }
        return list;
    }

    public String setStatus(Long projectId, String status) {
        Optional<Project> project = projectRepo.findById(projectId);
        Project proj = project.get();

        switch (status){
            case "approved":
                proj.setStatus(ApprovalStatus.APPROVED);
                this.updateProject(proj, projectId, proj.getStudent());
                return "Approved";

            case "rejected":
                proj.setStatus(ApprovalStatus.REJECTED);
                this.updateProject(proj, projectId, proj.getStudent());
                return "Rejected";

            default:
                return "Failed to update, Please try again";
        }
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }
}