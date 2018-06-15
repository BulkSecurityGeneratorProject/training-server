package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Problem.
 */
@Entity
@Table(name = "problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "problem_username")
    private String problemUsername;

    @Column(name = "problem_title")
    private String problemTitle;

    @Column(name = "problem_content")
    private String problemContent;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private User user;

    @ManyToOne
    private Forum forum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblemUsername() {
        return problemUsername;
    }

    public Problem problemUsername(String problemUsername) {
        this.problemUsername = problemUsername;
        return this;
    }

    public void setProblemUsername(String problemUsername) {
        this.problemUsername = problemUsername;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public Problem problemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
        return this;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public Problem problemContent(String problemContent) {
        this.problemContent = problemContent;
        return this;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public Problem reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public Problem reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public User getUser() {
        return user;
    }

    public Problem user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Forum getForum() {
        return forum;
    }

    public Problem forum(Forum forum) {
        this.forum = forum;
        return this;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
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
        Problem problem = (Problem) o;
        if (problem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Problem{" +
            "id=" + getId() +
            ", problemUsername='" + getProblemUsername() + "'" +
            ", problemTitle='" + getProblemTitle() + "'" +
            ", problemContent='" + getProblemContent() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
