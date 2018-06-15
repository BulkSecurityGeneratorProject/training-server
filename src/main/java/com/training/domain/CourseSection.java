package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CourseSection.
 */
@Entity
@Table(name = "course_section")
public class CourseSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_section_title")
    private String courseSectionTitle;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseSectionTitle() {
        return courseSectionTitle;
    }

    public CourseSection courseSectionTitle(String courseSectionTitle) {
        this.courseSectionTitle = courseSectionTitle;
        return this;
    }

    public void setCourseSectionTitle(String courseSectionTitle) {
        this.courseSectionTitle = courseSectionTitle;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public CourseSection reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public CourseSection reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public Course getCourse() {
        return course;
    }

    public CourseSection course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        CourseSection courseSection = (CourseSection) o;
        if (courseSection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseSection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseSection{" +
            "id=" + getId() +
            ", courseSectionTitle='" + getCourseSectionTitle() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
