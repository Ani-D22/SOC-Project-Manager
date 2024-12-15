package com.aniket.SOC_Project_Manager.repo;

import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//import java.util.Optional;


@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByStudent(Student student);
    List<Project> findByMentor(Mentor mentor);
}

//----------------------------------------------------------------------------------------------------------
//    Discarded Methods-------------------------------------------------------------------------------------

//    Optional<Project> findByStudentAndProject(Student student, Project project);

//    @Query("SELECT p FROM Project p WHERE p.Student.enrollment_no = :enrollment_no")
//    List<Project> findByStudentId(@Param("enrollment_no") String enrollment_no);
