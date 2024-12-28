package com.iamcodegym.SkillSync.Service;

import com.iamcodegym.SkillSync.Entity.jobrole;
import com.iamcodegym.SkillSync.Repository.JobRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    public List<jobrole> getAllJobRoles() {
        return jobRoleRepository.findAll();
    }
}

