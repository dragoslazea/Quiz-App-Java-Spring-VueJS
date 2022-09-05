package net.javaguides.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Enabled;
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
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String questionText;

    @ManyToOne
    @JoinColumn(name = "correct_option")
    private Option correctOption;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private Set<QuestionOption> options;

    @JsonIgnore
    @OneToMany(mappedBy = "subjectQuestion")
    private Set<QuizQuestion> quizQuestions;
}
