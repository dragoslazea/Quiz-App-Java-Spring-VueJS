package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Attempt;
import net.javaguides.springboot.model.QuestionOption;
import net.javaguides.springboot.model.Quiz;
import net.javaguides.springboot.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/attempt")
public class AttemptController {
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AttemptRepository attemptRepository;

    public  AttemptController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    public List<Attempt> getAllAttempts() {
        return attemptRepository.findAll();
    }

    @PostMapping
    public Attempt createAttempt(@RequestBody Attempt attempt) {
        Quiz quiz = attempt.getAttemptedQuiz();
        simpMessagingTemplate.convertAndSend("/websockets", "Quiz " + quiz.getChapter() + " was attempted!");
        return attemptRepository.save(attempt);
    }

    @GetMapping("{id}")
    public ResponseEntity<Attempt> getAttemptById(@PathVariable int id) {
        Attempt attempt = attemptRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No attempt with id: " + id));
        return ResponseEntity.ok(attempt);
    }

    @PutMapping("{id}")
    public ResponseEntity<Attempt> updateAttempt(@PathVariable int id, @RequestBody Attempt attemptDetails) {
        Attempt updatedAttempt = attemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No attempt with id: " + id));

        updatedAttempt.setAttemptedQuiz(attemptDetails.getAttemptedQuiz());
        updatedAttempt.setAttemptedStudent(attemptDetails.getAttemptedStudent());

        attemptRepository.save(updatedAttempt);

        return ResponseEntity.ok(updatedAttempt);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAttempt(@PathVariable int id) {
        Attempt attempt = attemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No attempt with id: " + id));

        attemptRepository.delete(attempt);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getByStudent/{id}")
    public List<Attempt> getAttemptsByStudent(@PathVariable int id) {
        List<Attempt> attempts = attemptRepository.findAll()
                .stream()
                .filter(x -> x.getAttemptedStudent().getId() == id)
                .collect(Collectors.toList());
        return attempts;
    }

    @GetMapping("getByQuiz/{id}")
    public List<Attempt> getAttemptsByQuiz(@PathVariable int id) {
        List<Attempt> attempts = attemptRepository.findAll()
                .stream()
                .filter(x -> x.getAttemptedQuiz().getId() == id)
                .collect(Collectors.toList());
        return attempts;
    }

    @MessageMapping("/notification")
    @SendTo("/websockets")
    public String sendNotification(String message) throws Exception {
        return message;
    }
}
