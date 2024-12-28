package com.iamcodegym.SkillSync.Service;

import com.iamcodegym.SkillSync.Entity.Keyword;
import com.iamcodegym.SkillSync.Entity.ParsedResultEntity;
import com.iamcodegym.SkillSync.Repository.KeywordRepository;
import com.iamcodegym.SkillSync.Repository.ParsedResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class ResumeService {
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private ParsedResultRepository parsedResultRepository;

//    public int parseResume(MultipartFile file, Long jobId) {
//        int foundKeywords = 0;
//
//        try {
//            // Extract text from the PDF file
//            String extractedText = extractTextFromPDF(file);
//
//            // Fetch keywords from the database using jobId
//            List<String> keywords = fetchKeywordsByJobId(jobId);
//
//            // Compare and count keywords found in the extracted text
//            foundKeywords = countMatchingKeywords(extractedText, keywords);
//
//            // Store results in the database
//            storeParsedResults(foundKeywords, jobId);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle file reading exceptions
//        }
//
//        return foundKeywords;
//    }

    public int parseResume(MultipartFile file, Long jobId) {
        int foundKeywords = 0;

        try {
            // Extract text from the PDF file
            String extractedText = extractTextFromPDF(file);

            // Log the extracted text
            System.out.println("Extracted Text: " + extractedText); // Log extracted text

            // Fetch keywords from the database using jobId
            List<String> keywords = fetchKeywordsByJobId(jobId);
            System.out.println("Keywords for Job ID " + jobId + ": " + keywords); // Log keywords

            if (!keywords.isEmpty()) {
                // Count keywords found in the extracted text
                foundKeywords = countMatchingKeywords(extractedText, keywords);
                System.out.println("Keywords found: " + foundKeywords); // Log found keywords
            }

            // Store results in the database
            storeParsedResults(foundKeywords, jobId);

        } catch (IOException e) {
            System.err.println("Error reading the PDF file: " + e.getMessage());
        }

        return foundKeywords;
    }

    // Extract text from a PDF file
    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document).toLowerCase().trim();
        }
    }

    // Fetch keywords based on jobId
//    private List<String> fetchKeywordsByJobId(Long jobId) {
//        List<Keyword> keywords = keywordRepository.findByJobId(jobId);
//        List<String> keywordList = new ArrayList<>();
//
//        for (Keyword keyword : keywords) {
//            // Split the comma-separated keywords and normalize them
//            String[] splitKeywords = keyword.getKeywords().toLowerCase().trim().split(",");
//            for (String kw : splitKeywords) {
//                keywordList.add(kw.trim()); // Trim spaces around each keyword
//            }
//        }
//        return keywordList;
//    }

    private List<String> fetchKeywordsByJobId(Long jobId) {
        List<Keyword> keywords = keywordRepository.findKeywordsByJobId(jobId);
        List<String> keywordList = new ArrayList<>();

        // Log the number of keywords fetched
        System.out.println("Number of keywords fetched for jobId " + jobId + ": " + keywords.size());

        for (Keyword keyword : keywords) {
            // Log the keyword string being processed
            System.out.println("Processing keywords: " + keyword.getKeywords());

            // Split the comma-separated keywords and normalize them
            String[] splitKeywords = keyword.getKeywords().toLowerCase().trim().split(",");
            for (String kw : splitKeywords) {
                keywordList.add(kw.trim()); // Trim spaces around each keyword
            }
        }
        return keywordList;
    }

    // Count matching keywords with flexible matching
    private int countMatchingKeywords(String extractedText, List<String> keywords) {
        int matchCount = 0;

        // Use regex to handle partial matches and variations
        for (String keyword : keywords) {
            String regex = "\\b" + Pattern.quote(keyword) + "\\b"; // word-boundary match to avoid partial word matches
            if (Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(extractedText).find()) {
                matchCount++;
            }
        }

        return matchCount;
    }

    // Store the parsed results
    private void storeParsedResults(int foundKeywords, Long jobId) {
        ParsedResultEntity result = new ParsedResultEntity();
        result.setJobId(jobId);
        result.setKeywordsFound(foundKeywords);
        result.setCreatedAt(LocalDateTime.now());
        parsedResultRepository.save(result);
    }


    public List<String> suggestMissingSkills(MultipartFile file, Long jobId) {
        List<String> missingSkills = new ArrayList<>();

        try {
            // Extract text from the PDF file
            String extractedText = extractTextFromPDF(file).toLowerCase();

            // Fetch keywords (skills) from the database using jobId
            List<String> databaseSkills = fetchKeywordsByJobId(jobId);

            // Convert database skills to lowercase for comparison
            List<String> lowerCaseDatabaseSkills = new ArrayList<>();
            for (String skill : databaseSkills) {
                lowerCaseDatabaseSkills.add(skill.toLowerCase().trim());
            }

            // Identify missing skills
            for (String skill : lowerCaseDatabaseSkills) {
                if (!extractedText.contains(skill)) {
                    missingSkills.add(skill); // Keep the original case
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return missingSkills;
    }


    private Set<String> extractSkillsFromText(String extractedText) {
        Set<String> skills = new HashSet<>();
        String[] skillArray = extractedText.split(",");
        for (String skill : skillArray) {
            skills.add(skill.trim().toLowerCase());
        }
        return skills;
    }

}
