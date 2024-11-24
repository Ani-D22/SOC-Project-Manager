package com.aniket.SOC_Project_Manager.controller;


import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.service.JwtService;
import com.aniket.SOC_Project_Manager.service.StudentService;
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
public class StudentController {
    @Autowired
    private final StudentService studentService;

    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/student/signup")
    public ResponseEntity<Student> signup(@RequestBody Student student) {

        student.setPassword(encoder.encode(student.getPassword()));
        System.out.println("Encrypted using BCrypt Password : " + student.getPassword());

        return ResponseEntity.ok(studentService.signup(student));
    }

    @PostMapping("/student/signin")
    public String signin(@RequestBody Student student){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(student.getEmail(),student.getPassword()));

        if(authentication.isAuthenticated())    return jwtService.generateToken(student.getEmail());//"Login Successful..!";
        else    return "Login Failed...";
    }
}
