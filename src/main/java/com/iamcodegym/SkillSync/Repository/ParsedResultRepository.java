package com.iamcodegym.SkillSync.Repository;

import com.iamcodegym.SkillSync.Entity.ParsedResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsedResultRepository extends JpaRepository<ParsedResultEntity, Long> {
}
