package com.ll.feelko.domain.experience.dao;

import com.ll.feelko.domain.experience.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
