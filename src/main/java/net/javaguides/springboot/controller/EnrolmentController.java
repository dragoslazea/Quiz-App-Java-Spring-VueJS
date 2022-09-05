package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Enrolment;
import net.javaguides.springboot.model.Subject;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.EnrolmentRepository;
import net.javaguides.springboot.repository.SubjectRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/enrolment")
public class EnrolmentController {
    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    public List<Enrolment> getAllEnrolments() {
        return enrolmentRepository.findAll();
    }

    @PostMapping
    public Enrolment createEnrolment(@RequestBody Enrolment enrolment) {
        return enrolmentRepository.save(enrolment);
    }

    @PostMapping("create/{idStudent}/{idSubject}")
    public Enrolment createNewEnrolment(@PathVariable int idStudent, @PathVariable int idSubject) {
        User student = userRepository.findById(idStudent)
                .orElseThrow(() -> new ResourceNotFoundException("No such student"));
        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(() -> new ResourceNotFoundException("No such subject"));
        Enrolment enrolment = new Enrolment();
        enrolment.setSubject(subject);
        enrolment.setStudent(student);
        return enrolmentRepository.save(enrolment);
    }

    @GetMapping("{id}")
    public ResponseEntity<Enrolment> getEnrolmentById(@PathVariable int id) {
        Enrolment enrolment = enrolmentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No enrolment with id: " + id));
        return ResponseEntity.ok(enrolment);
    }

    @PutMapping("{id}")
    public ResponseEntity<Enrolment> updateEnrolment(@PathVariable int id, @RequestBody Enrolment enrolmentDetails) {
        Enrolment updatedEnrolment = enrolmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No enrolment with id: " + id));

        updatedEnrolment.setStudent(enrolmentDetails.getStudent());
        updatedEnrolment.setSubject(enrolmentDetails.getSubject());

        enrolmentRepository.save(updatedEnrolment);

        return ResponseEntity.ok(updatedEnrolment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEnrolment(@PathVariable int id) {
        Enrolment enrolment = enrolmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No question-option with id: " + id));

        enrolmentRepository.delete(enrolment);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getByUsername/{username}")
    public ResponseEntity<List<Enrolment>> getEnrolmentsByUsername(@PathVariable String username) {
        User student = userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("No user with username " + username));
        List<Enrolment> enrolments = enrolmentRepository.findEnrolmentsByStudent(student)
                .orElseThrow(() -> new ResourceNotFoundException("No enrolments for student " + username));
        return ResponseEntity.ok(enrolments);
    }

    @GetMapping("getByStudent/{id}")
    public List<Enrolment> getEnrolmentsByStudentId(@PathVariable int id) {
        List<Enrolment> enrolments = enrolmentRepository.findAll()
                .stream()
                .filter(x -> x.getStudent().getId() == id)
                .collect(Collectors.toList());
        return enrolments;
    }

    @PostMapping("getStudent")
    public ResponseEntity<List<Enrolment>> getEnrolmentsByStudent(@RequestBody  User student) {
        List<Enrolment> enrolments = enrolmentRepository.findEnrolmentsByStudent(student)
                .orElseThrow(() -> new ResourceNotFoundException("Student " + student.getUsername() + " is not enrolled in any course"));
        return ResponseEntity.ok(enrolments);
    }
}
