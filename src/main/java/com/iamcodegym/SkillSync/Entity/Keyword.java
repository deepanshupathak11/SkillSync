package com.iamcodegym.SkillSync.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyword_id; // This should be your primary key

    @Column(name = "jobId")
    private Long jobId;

    @Column(length = 5000)
    private String keywords;

    // Getters and Setters
    public Long getKeywordId() {
        return keyword_id;
    }

    public void setKeywordId(Long keywordId) {
        this.keyword_id = keywordId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}