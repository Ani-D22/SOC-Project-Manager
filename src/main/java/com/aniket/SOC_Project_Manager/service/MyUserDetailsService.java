package com.aniket.SOC_Project_Manager.service;

import com.aniket.SOC_Project_Manager.model.Faculty;
import com.aniket.SOC_Project_Manager.model.FacultyPrincipal;
import com.aniket.SOC_Project_Manager.model.Student;
import com.aniket.SOC_Project_Manager.model.StudentPrincipal;
import com.aniket.SOC_Project_Manager.repo.FacultyRepo;
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
    private FacultyRepo facultyRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(email);

        if(student == null){
            System.out.println("student not found");
            Faculty faculty = facultyRepo.findByEmail(email);
            if(faculty != null){
                System.out.println("faculty found");
                return new FacultyPrincipal(faculty);
            }
            System.out.println("student not found 2");
            System.out.println("User 404 : User not found !");
            throw new UsernameNotFoundException(email+": User 404");
        }

        return new StudentPrincipal(student);
    }
}
