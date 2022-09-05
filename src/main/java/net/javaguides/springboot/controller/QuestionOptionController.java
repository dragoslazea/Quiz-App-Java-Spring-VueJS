package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.QuestionOption;
import net.javaguides.springboot.repository.QuestionOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/questionoption")
public class QuestionOptionController {
    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @GetMapping
    public List<QuestionOption> getAllQuestionOptions() {
        return questionOptionRepository.findAll();
    }

    @PostMapping
    public QuestionOption createQuestionOption(@RequestBody QuestionOption questionOption) {
        return questionOptionRepository.save(questionOption);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionOption> getQuestionOptionById(@PathVariable int id) {
        QuestionOption questionOption = questionOptionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No question-option with id: " + id));
        return ResponseEntity.ok(questionOption);
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionOption> updateQuestionOption(@PathVariable int id, @RequestBody QuestionOption questionOptionDetails) {
        QuestionOption updatedQuestionOption = questionOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No question-option with id: " + id));

        updatedQuestionOption.setQuestion(questionOptionDetails.getQuestion());
        updatedQuestionOption.setOption(questionOptionDetails.getOption());

        questionOptionRepository.save(updatedQuestionOption);

        return ResponseEntity.ok(updatedQuestionOption);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuestionOption(@PathVariable int id) {
        QuestionOption questionOption = questionOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No question-option with id: " + id));

        questionOptionRepository.delete(questionOption);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getByQuestion/{id}")
    public List<QuestionOption> getQuestionOptionByQuestion(@PathVariable int id) {
        List<QuestionOption> questionOptions = questionOptionRepository.findAll()
                .stream()
                .filter(x -> x.getQuestion().getId() == id)
                .collect(Collectors.toList());
        return questionOptions;
    }

    @GetMapping("getByOption/{id}")
    public List<QuestionOption> getQuestionOptionByOption(@PathVariable int id) {
        List<QuestionOption> questionOptions = questionOptionRepository.findAll()
                .stream()
                .filter(x -> x.getOption().getId() == id)
                .collect(Collectors.toList());
        return questionOptions;
    }

    @GetMapping("getByQuesionAndOption/{idQuestion}/{idOption}")
    public QuestionOption getQuestionOptionByQuestionAndOption(@PathVariable int idQuestion, @PathVariable int idOption) {
        List<QuestionOption> questionOptions = questionOptionRepository.findAll()
                .stream()
                .filter(x -> x.getQuestion().getId() == idQuestion)
                .filter(x -> x.getOption().getId() == idOption)
                .collect(Collectors.toList());
        return questionOptions.get(0);
    }
}
