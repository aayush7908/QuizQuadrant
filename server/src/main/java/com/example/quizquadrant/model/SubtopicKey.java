package com.example.quizquadrant.model;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class SubtopicKey implements Serializable {
    private Long id;
    private Subject subject;

    public SubtopicKey(Long id, Subject subject) {
        this.id = id;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


}