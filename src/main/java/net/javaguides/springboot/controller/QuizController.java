package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Enrolment;
import net.javaguides.springboot.model.Question;
import net.javaguides.springboot.model.Quiz;
import net.javaguides.springboot.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @GetMapping("{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
        Quiz quiz = quizRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No quiz with id: " + id));
        return ResponseEntity.ok(quiz);
    }

    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable int id, @RequestBody Quiz quizDetails) {
        Quiz updatedQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz with id: " + id));

        updatedQuiz.setChapter(quizDetails.getChapter());
        updatedQuiz.setQuizSubject(quizDetails.getQuizSubject());
        updatedQuiz.setTeacher(quizDetails.getTeacher());

        quizRepository.save(updatedQuiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable int id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz with id: " + id));

        quizRepository.delete(quiz);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getChapter/{chapter}")
    public ResponseEntity<Quiz> getQuizByChapter(@PathVariable String chapter) {
        Quiz quiz = quizRepository.findByChapter(chapter).
                orElseThrow(() -> new ResourceNotFoundException("No quiz of chapter: " + chapter));
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("getBySubject/{id}")
    public List<Quiz> getQuizzesBySubject(@PathVariable int id) {
        List<Quiz> quizzes = quizRepository.findAll()
                .stream()
                .filter(x -> x.getQuizSubject().getId() == id)
                .collect(Collectors.toList());
        return quizzes;
    }

    @GetMapping("getByTeacher/{id}")
    public List<Quiz> getQuizzesByTeacher(@PathVariable int id) {
        List<Quiz> quizzes = quizRepository.findAll()
                .stream()
                .filter(x -> x.getTeacher().getId() == id)
                .collect(Collectors.toList());
        return quizzes;
    }
}
