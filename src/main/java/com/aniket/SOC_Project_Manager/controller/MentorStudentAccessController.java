package com.aniket.SOC_Project_Manager.controller;

import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.Project;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.repo.MentorRepo;
import com.aniket.SOC_Project_Manager.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//MENTOR PERSPECTIVE
@RestController
@RequestMapping("/api/mentor/students")
@RequiredArgsConstructor
public class MentorStudentAccessController {
    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private StudentRepo studentRepo;

    //Show all the students on app
    @GetMapping
    public ResponseEntity<List<Student>> viewProjects(){
        return ResponseEntity.ok(studentRepo.findAll());
    }

    //Show students under the mentor
    @GetMapping("/mentorStudents")
    public ResponseEntity<List<Student>> viewProjects(@AuthenticationPrincipal UserDetails mentorDetails){
        Mentor mentor = mentorRepo.findByEmail(mentorDetails.getUsername());

        return ResponseEntity.ok(studentRepo.findByMentor(mentor));
    }

    //Show student by enrollment_no
    @GetMapping("/{enrollment_no}")
    public ResponseEntity<Student> getproject(@PathVariable String enrollment_no) {
        Student student = studentRepo.findByEnrollment_no(enrollment_no);

        if(student.getEnrollment_no() != null) return new ResponseEntity<>(student, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
