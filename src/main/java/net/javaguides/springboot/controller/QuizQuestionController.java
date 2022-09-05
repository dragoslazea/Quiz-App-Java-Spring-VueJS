package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.QuizQuestion;
import net.javaguides.springboot.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/quizquestion")
public class QuizQuestionController {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @GetMapping
    public List<QuizQuestion> getAllQuizQuestions() {
        return quizQuestionRepository.findAll();
    }

    @PostMapping
    public QuizQuestion createQuizQuestion(@RequestBody QuizQuestion quizQuestion) {
        return quizQuestionRepository.save(quizQuestion);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizQuestion> getQuizQuestionById(@PathVariable int id) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No quiz-question with id: " + id));
        return ResponseEntity.ok(quizQuestion);
    }

    @PutMapping("{id}")
    public ResponseEntity<QuizQuestion> updateQuestion(@PathVariable int id, @RequestBody QuizQuestion quizQuestionDetails) {
        QuizQuestion updatedQuizQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz-question with id: " + id));

        updatedQuizQuestion.setSubjectQuestion(quizQuestionDetails.getSubjectQuestion());
        updatedQuizQuestion.setQuizQ(quizQuestionDetails.getQuizQ());

        quizQuestionRepository.save(updatedQuizQuestion);

        return ResponseEntity.ok(updatedQuizQuestion);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuizQuestion(@PathVariable int id) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz-question with id: " + id));

        quizQuestionRepository.delete(quizQuestion);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getByQuiz/{id}")
    public List<QuizQuestion> getQuizQuestionsByQuiz(@PathVariable int id) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll()
                .stream()
                .filter(x -> x.getQuizQ().getId() == id)
                .collect(Collectors.toList());
        return quizQuestions;
    }
}
