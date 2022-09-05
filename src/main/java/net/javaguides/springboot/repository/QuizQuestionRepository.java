package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {

}
