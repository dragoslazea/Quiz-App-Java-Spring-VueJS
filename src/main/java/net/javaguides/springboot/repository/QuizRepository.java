package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Optional<Quiz> findByChapter(String chapter);
}
