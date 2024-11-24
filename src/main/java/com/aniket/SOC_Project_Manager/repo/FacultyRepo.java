package com.aniket.SOC_Project_Manager.repo;
import com.aniket.SOC_Project_Manager.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacultyRepo extends JpaRepository<Faculty, Long> {
    Faculty findByEmail(String email);
    boolean existsByEmail(String email);
}