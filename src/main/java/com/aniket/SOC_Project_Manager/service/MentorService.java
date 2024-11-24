package com.aniket.SOC_Project_Manager.service;


import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.repo.MentorRepo;
import com.aniket.SOC_Project_Manager.repo.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MentorService {
    @Autowired
    private final MentorRepo mentorRepo;
    @Autowired
    private final StudentRepo studentRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;

    public Mentor signup(Mentor mentor) {
        if (mentorRepo.existsByEmail(mentor.getEmail())) {
            throw new RuntimeException("Email already registered as a mentor");
        }

        if (studentRepo.existsByEmail(mentor.getEmail())) {
            throw new RuntimeException("Email already registered as a student");
        }
        return mentorRepo.save(mentor);
    }
}