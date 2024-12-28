package com.iamcodegym.SkillSync.Controller;

import com.iamcodegym.SkillSync.Entity.jobrole;
import com.iamcodegym.SkillSync.Service.JobRoleService;
import com.iamcodegym.SkillSync.Service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobRoleService jobRoleService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file, @RequestParam("jobId") Long jobId) {
        // Check if the file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        // Check the file type
        String contentType = file.getContentType();
        if (!"application/pdf".equals(contentType) &&
                !"application/x-pdf".equals(contentType) &&
                !"application/octet-stream".equals(contentType)) {
            return ResponseEntity.badRequest().body("Wrong file type. Please upload a PDF file.");
        }

        // Process resume parsing
        int foundKeywords = resumeService.parseResume(file, jobId);

        // Fetch job roles using the GET API logic


        // Fetch suggestions for missing skills
        List<String> missingSkills = resumeService.suggestMissingSkills(file, jobId);

        // Create a response that includes found keywords, job roles, and missing skills
        Map<String, Object> response = new HashMap<>();
        response.put("foundKeywords", foundKeywords);

        response.put("missingSkills", missingSkills);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/suggestions/{jobId}")
    public List<String> getMissingSkills(@RequestParam("file") MultipartFile file, @PathVariable Long jobId) {
        return resumeService.suggestMissingSkills(file , jobId);
    }
}
