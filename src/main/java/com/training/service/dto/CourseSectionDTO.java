package com.training.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CourseSection entity.
 */
public class CourseSectionDTO implements Serializable {

    private Long id;

    private String courseSectionTitle;

    private String reservedOne;

    private String reservedTwo;

    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseSectionTitle() {
        return courseSectionTitle;
    }

    public void setCourseSectionTitle(String courseSectionTitle) {
        this.courseSectionTitle = courseSectionTitle;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseSectionDTO courseSectionDTO = (CourseSectionDTO) o;
        if(courseSectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseSectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseSectionDTO{" +
            "id=" + getId() +
            ", courseSectionTitle='" + getCourseSectionTitle() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
