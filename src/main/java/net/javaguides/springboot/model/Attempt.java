package net.javaguides.springboot.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Data
@Table(name = "attempt")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    private int maxScore;

    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz attemptedQuiz;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private User attemptedStudent;
}
