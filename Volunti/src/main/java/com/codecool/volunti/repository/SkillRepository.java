package com.codecool.volunti.repository;

import com.codecool.volunti.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository  extends CrudRepository<Skill, Integer> {
}
