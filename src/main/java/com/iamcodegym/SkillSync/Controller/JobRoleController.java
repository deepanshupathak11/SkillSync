package com.iamcodegym.SkillSync.Controller;

import com.iamcodegym.SkillSync.Entity.jobrole;
import com.iamcodegym.SkillSync.Repository.JobRoleRepository;
import com.iamcodegym.SkillSync.Service.JobRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/jobRoles")
public class JobRoleController {

    @Autowired
    private JobRoleService jobRoleService;
    @Autowired    JobRoleRepository jobRoleRepository;

    @GetMapping
    public List<jobrole> getAllJobRoles() {
        //return jobRoleService.getAllJobRoles();
        List<jobrole> jobRoles = jobRoleRepository.findAll();

        // Clean up job role names by trimming
        jobRoles.forEach(jobRole -> {
            jobRole.setJobRole(jobRole.getJobRole().trim());
        });

        return jobRoles;
    }
}