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
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String chapter;

    @ManyToOne
    @JoinColumn(name = "teacher")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Subject quizSubject;

    @JsonIgnore
    @OneToMany(mappedBy = "quizQ")
    private Set<QuizQuestion> quizQuestions;

    @JsonIgnore
    @OneToMany(mappedBy = "attemptedQuiz")
    private Set<Attempt> attempts;

}
