package com.aniket.SOC_Project_Manager.repo;
import com.aniket.SOC_Project_Manager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    boolean existsByEmail(String email);
}