package com.aniket.SOC_Project_Manager.service;


import com.aniket.SOC_Project_Manager.model.Faculty;
import com.aniket.SOC_Project_Manager.repo.FacultyRepo;
import com.aniket.SOC_Project_Manager.repo.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FacultyService {
    @Autowired
    private final FacultyRepo facultyRepo;
    @Autowired
    private final StudentRepo studentRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;

    public Faculty signup(Faculty faculty) {
        if (facultyRepo.existsByEmail(faculty.getEmail())) {
            throw new RuntimeException("Email already registered as a faculty");
        }

        if (studentRepo.existsByEmail(faculty.getEmail())) {
            throw new RuntimeException("Email already registered as a student");
        }
        return facultyRepo.save(faculty);
    }
}