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
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private Set<Enrolment> enrolledSubjects;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Quiz> quizzes;

    @JsonIgnore
    @OneToMany(mappedBy = "attemptedStudent")
    private Set<Attempt> attempts;
}
