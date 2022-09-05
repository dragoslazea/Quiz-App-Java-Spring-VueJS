package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Option;
import net.javaguides.springboot.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/ansoption")
public class OptionController {
    @Autowired
    private OptionRepository optionRepository;

    @GetMapping
    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    @PostMapping
    public Option createOption(@RequestBody Option option) {
        return optionRepository.save(option);
    }

    @GetMapping("{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable int id) {
        Option option = optionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No option with id: " + id));
        return ResponseEntity.ok(option);
    }

    @PutMapping("{id}")
    public ResponseEntity<Option> updateOption(@PathVariable int id, @RequestBody Option optionDetails) {
        Option updateOption = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No subject with id: " + id));

        updateOption.setAnswer(optionDetails.getAnswer());

        optionRepository.save(updateOption);

        return ResponseEntity.ok(updateOption);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteOption(@PathVariable int id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No option with id: " + id));

        optionRepository.delete(option);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("getAnswer/{answer}")
    public ResponseEntity<Option> getOptionByAnswer(@PathVariable String answer) {
        Option option = optionRepository.findByAnswer(answer).
                orElseThrow(() -> new ResourceNotFoundException("No option with text: " + answer));
        return ResponseEntity.ok(option);
    }
}
