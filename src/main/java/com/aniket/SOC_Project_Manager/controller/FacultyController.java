package com.aniket.SOC_Project_Manager.controller;


import com.aniket.SOC_Project_Manager.model.Faculty;
import com.aniket.SOC_Project_Manager.service.JwtService;
import com.aniket.SOC_Project_Manager.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FacultyController {
    @Autowired
    private final FacultyService facultyService;

    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/faculty/signup")
    public ResponseEntity<Faculty> signup(@RequestBody Faculty faculty) {

        faculty.setPassword(encoder.encode(faculty.getPassword()));
        System.out.println("Encrypted using BCrypt Password : " + faculty.getPassword());

        return ResponseEntity.ok(facultyService.signup(faculty));
    }

    @PostMapping("/faculty/signin")
    public String signin(@RequestBody Faculty faculty){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(faculty.getEmail(),faculty.getPassword()));

        if(authentication.isAuthenticated())    return jwtService.generateToken(faculty.getEmail());//"Login Successful..!";
        else    return "Login Failed...";
    }
}
