package com.aniket.SOC_Project_Manager.service;

import com.aniket.SOC_Project_Manager.model.Mentor;
import com.aniket.SOC_Project_Manager.model.MentorPrincipal;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.model.StudentPrincipal;
import com.aniket.SOC_Project_Manager.repo.MentorRepo;
import com.aniket.SOC_Project_Manager.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private MentorRepo mentorRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(email);

        if(student == null){
            System.out.println("student not found");
            Mentor mentor = mentorRepo.findByEmail(email);
            if(mentor != null){
                System.out.println("mentor found");
                return new MentorPrincipal(mentor);
            }
            System.out.println("student not found 2");
            System.out.println("User 404 : User not found !");
            throw new UsernameNotFoundException(email+": User 404");
        }

        return new StudentPrincipal(student);
    }
}
