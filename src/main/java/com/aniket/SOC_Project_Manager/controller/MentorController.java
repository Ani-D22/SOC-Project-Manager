package com.aniket.SOC_Project_Manager.controller;


import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.service.JwtService;
import com.aniket.SOC_Project_Manager.service.MentorService;
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
@RequestMapping("/api/mentor")
@RequiredArgsConstructor
public class MentorController {
    @Autowired
    private final MentorService mentorService;

    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/signup")
    public ResponseEntity<Mentor> signup(@RequestBody Mentor mentor) {

        mentor.setPassword(encoder.encode(mentor.getPassword()));
        System.out.println("Encrypted using BCrypt Password : " + mentor.getPassword());

        return ResponseEntity.ok(mentorService.signup(mentor));
    }

    @PostMapping("/signin")
    public String signin(@RequestBody Mentor mentor){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(mentor.getEmail(),mentor.getPassword()));

        if(authentication.isAuthenticated())    return jwtService.generateToken(mentor.getEmail());//"Login Successful..!";
        else    return "Login Failed...";
    }
}
