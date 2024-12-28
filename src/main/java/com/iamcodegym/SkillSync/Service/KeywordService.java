package com.iamcodegym.SkillSync.Service;

import com.iamcodegym.SkillSync.Entity.Keyword;
import com.iamcodegym.SkillSync.Repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {
    @Autowired
    private KeywordRepository keywordRepository;

    // Method to fetch keywords for a specific jobId
    public List<String> getKeywordsByJobId(Long jobId) {
        List<Keyword> keywords = keywordRepository.findKeywordsByJobId(jobId);
        return keywords.stream().map(Keyword::getKeywords).toList(); // Convert to List<String>
    }
}

