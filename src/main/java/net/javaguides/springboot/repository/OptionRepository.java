package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    Optional<Option> findByAnswer(String answer);
}
