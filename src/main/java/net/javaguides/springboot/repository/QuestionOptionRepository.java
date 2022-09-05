package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Integer> {
}

