package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_username")
    private String answerUsername;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "answer_correct")
    private Boolean answerCorrect;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private User user;

    @ManyToOne
    private Problem problem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerUsername() {
        return answerUsername;
    }

    public Answer answerUsername(String answerUsername) {
        this.answerUsername = answerUsername;
        return this;
    }

    public void setAnswerUsername(String answerUsername) {
        this.answerUsername = answerUsername;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public Answer answerContent(String answerContent) {
        this.answerContent = answerContent;
        return this;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Boolean isAnswerCorrect() {
        return answerCorrect;
    }

    public Answer answerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
        return this;
    }

    public void setAnswerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public Answer reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public Answer reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public User getUser() {
        return user;
    }

    public Answer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Problem getProblem() {
        return problem;
    }

    public Answer problem(Problem problem) {
        this.problem = problem;
        return this;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        if (answer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", answerUsername='" + getAnswerUsername() + "'" +
            ", answerContent='" + getAnswerContent() + "'" +
            ", answerCorrect='" + isAnswerCorrect() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
