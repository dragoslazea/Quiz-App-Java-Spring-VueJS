package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findByQuestionText(String questionText);
}
