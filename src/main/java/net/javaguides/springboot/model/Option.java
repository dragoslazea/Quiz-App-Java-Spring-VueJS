package net.javaguides.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "ansoption")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String answer;

    @JsonIgnore
    @OneToMany(mappedBy = "correctOption")
    Set<Question> questionSet;

    @JsonIgnore
    @OneToMany(mappedBy = "option")
    private Set<QuestionOption> questions;
}
