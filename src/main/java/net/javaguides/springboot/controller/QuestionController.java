package net.javaguides.springboot.controller;


import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Question;
import net.javaguides.springboot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        Question question = questionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No question with id: " + id));
        return ResponseEntity.ok(question);
    }

    @PutMapping("{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question questionDetails) {
        Question updatedQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subject with id: " + id));

        updatedQuestion.setQuestionText(questionDetails.getQuestionText());
        updatedQuestion.setCorrectOption(questionDetails.getCorrectOption());

        questionRepository.save(updatedQuestion);

        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable int id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No question with id: " + id));

        questionRepository.delete(question);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getText/{questionText}")
    public ResponseEntity<Question> getQuestionByText(@PathVariable String questionText) {
        Question question = questionRepository.findByQuestionText(questionText).
                orElseThrow(() -> new ResourceNotFoundException("No question with text: " + questionText));
        return ResponseEntity.ok(question);
    }
}
