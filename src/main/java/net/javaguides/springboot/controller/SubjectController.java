package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Quiz;
import net.javaguides.springboot.model.Subject;
import net.javaguides.springboot.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @GetMapping("{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable int id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subject with id: " + id));
        return ResponseEntity.ok(subject);
    }

    @PutMapping("{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable int id, @RequestBody Subject subjectDetails) {
        Subject updateSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subject with id: " + id));

        updateSubject.setName(subjectDetails.getName());
        updateSubject.setDescription(subjectDetails.getDescription());

        subjectRepository.save(updateSubject);

        return ResponseEntity.ok(updateSubject);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteSubject(@PathVariable int id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subject with id: " + id));

        subjectRepository.delete(subject);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getName/{name}")
    public ResponseEntity<Subject> getSubjectByName(@PathVariable String name) {
        Subject subject = subjectRepository.findByName(name).
                orElseThrow(() -> new ResourceNotFoundException("No subject with name: " + name));
        return ResponseEntity.ok(subject);
    }
}
