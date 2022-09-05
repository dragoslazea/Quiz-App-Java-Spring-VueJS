package net.javaguides.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.Set;

// Comentez cand am nevoie de constructori pentru alte chestii
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "subject")
    private Set<Enrolment> enrolledStudents;

    @JsonIgnore
    @OneToMany(mappedBy = "quizSubject")
    private Set<Quiz> quizzes;
}
