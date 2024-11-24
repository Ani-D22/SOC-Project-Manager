package com.aniket.SOC_Project_Manager.repo;
import com.aniket.SOC_Project_Manager.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MentorRepo extends JpaRepository<Mentor, Long> {
    Mentor findByEmail(String email);
    boolean existsByEmail(String email);
}