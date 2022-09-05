package net.javaguides.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "quiz_question")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quizQ;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question subjectQuestion;
}
