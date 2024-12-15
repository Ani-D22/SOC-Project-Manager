package com.aniket.SOC_Project_Manager.repo;
import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByEnrollment_no(String enrollment_no);
    Student findByEmail(String email);
    boolean existsByEmail(String email);
    List<Student> findByMentor(Mentor mentor);
}