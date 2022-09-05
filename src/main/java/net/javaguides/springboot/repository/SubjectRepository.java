package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByName(String name);
}
