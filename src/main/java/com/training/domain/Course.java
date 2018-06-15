package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_img")
    private String courseImg;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "course_level")
    private String courseLevel;

    @Column(name = "course_phase")
    private String coursePhase;

    @Column(name = "course_content")
    private String courseContent;

    @Column(name = "course_teacher")
    private String courseTeacher;

    @Column(name = "course_time")
    private Integer courseTime;

    @Column(name = "course_people")
    private Integer coursePeople;

    @Column(name = "course_star")
    private Integer courseStar;

    @Column(name = "course_price")
    private Integer coursePrice;

    @Column(name = "course_recommend_index")
    private Integer courseRecommendIndex;

    @Column(name = "course_new_recommendations")
    private Integer courseNewRecommendations;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private CareerPath careerPath;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public Course courseImg(String courseImg) {
        this.courseImg = courseImg;
        return this;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseType() {
        return courseType;
    }

    public Course courseType(String courseType) {
        this.courseType = courseType;
        return this;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public Course courseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
        return this;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCoursePhase() {
        return coursePhase;
    }

    public Course coursePhase(String coursePhase) {
        this.coursePhase = coursePhase;
        return this;
    }

    public void setCoursePhase(String coursePhase) {
        this.coursePhase = coursePhase;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public Course courseContent(String courseContent) {
        this.courseContent = courseContent;
        return this;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public Course courseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
        return this;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public Integer getCourseTime() {
        return courseTime;
    }

    public Course courseTime(Integer courseTime) {
        this.courseTime = courseTime;
        return this;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getCoursePeople() {
        return coursePeople;
    }

    public Course coursePeople(Integer coursePeople) {
        this.coursePeople = coursePeople;
        return this;
    }

    public void setCoursePeople(Integer coursePeople) {
        this.coursePeople = coursePeople;
    }

    public Integer getCourseStar() {
        return courseStar;
    }

    public Course courseStar(Integer courseStar) {
        this.courseStar = courseStar;
        return this;
    }

    public void setCourseStar(Integer courseStar) {
        this.courseStar = courseStar;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public Course coursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
        return this;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getCourseRecommendIndex() {
        return courseRecommendIndex;
    }

    public Course courseRecommendIndex(Integer courseRecommendIndex) {
        this.courseRecommendIndex = courseRecommendIndex;
        return this;
    }

    public void setCourseRecommendIndex(Integer courseRecommendIndex) {
        this.courseRecommendIndex = courseRecommendIndex;
    }

    public Integer getCourseNewRecommendations() {
        return courseNewRecommendations;
    }

    public Course courseNewRecommendations(Integer courseNewRecommendations) {
        this.courseNewRecommendations = courseNewRecommendations;
        return this;
    }

    public void setCourseNewRecommendations(Integer courseNewRecommendations) {
        this.courseNewRecommendations = courseNewRecommendations;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public Course reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public Course reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public CareerPath getCareerPath() {
        return careerPath;
    }

    public Course careerPath(CareerPath careerPath) {
        this.careerPath = careerPath;
        return this;
    }

    public void setCareerPath(CareerPath careerPath) {
        this.careerPath = careerPath;
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
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", courseImg='" + getCourseImg() + "'" +
            ", courseType='" + getCourseType() + "'" +
            ", courseLevel='" + getCourseLevel() + "'" +
            ", coursePhase='" + getCoursePhase() + "'" +
            ", courseContent='" + getCourseContent() + "'" +
            ", courseTeacher='" + getCourseTeacher() + "'" +
            ", courseTime=" + getCourseTime() +
            ", coursePeople=" + getCoursePeople() +
            ", courseStar=" + getCourseStar() +
            ", coursePrice=" + getCoursePrice() +
            ", courseRecommendIndex=" + getCourseRecommendIndex() +
            ", courseNewRecommendations=" + getCourseNewRecommendations() +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
