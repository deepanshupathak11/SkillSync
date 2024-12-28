package com.iamcodegym.SkillSync.Repository;

import com.iamcodegym.SkillSync.Entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k FROM Keyword k WHERE k.jobId = :jobId")
    List<Keyword> findKeywordsByJobId(@Param("jobId") Long jobId);
}
