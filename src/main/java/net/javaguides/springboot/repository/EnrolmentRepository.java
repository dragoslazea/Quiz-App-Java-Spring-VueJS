package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Enrolment;
import net.javaguides.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {
    Optional<List<Enrolment>> findEnrolmentsByStudent(User student);
}
