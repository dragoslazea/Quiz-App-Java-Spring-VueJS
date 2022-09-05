package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Integer> {
}
