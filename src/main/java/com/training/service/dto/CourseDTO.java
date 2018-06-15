package com.training.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Course entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    private String courseName;

    private String courseImg;

    private String courseType;

    private String courseLevel;

    private String coursePhase;

    private String courseContent;

    private String courseTeacher;

    private Integer courseTime;

    private Integer coursePeople;

    private Integer courseStar;

    private Integer coursePrice;

    private Integer courseRecommendIndex;

    private Integer courseNewRecommendations;

    private String reservedOne;

    private String reservedTwo;

    private Long careerPathId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCoursePhase() {
        return coursePhase;
    }

    public void setCoursePhase(String coursePhase) {
        this.coursePhase = coursePhase;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getCoursePeople() {
        return coursePeople;
    }

    public void setCoursePeople(Integer coursePeople) {
        this.coursePeople = coursePeople;
    }

    public Integer getCourseStar() {
        return courseStar;
    }

    public void setCourseStar(Integer courseStar) {
        this.courseStar = courseStar;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getCourseRecommendIndex() {
        return courseRecommendIndex;
    }

    public void setCourseRecommendIndex(Integer courseRecommendIndex) {
        this.courseRecommendIndex = courseRecommendIndex;
    }

    public Integer getCourseNewRecommendations() {
        return courseNewRecommendations;
    }

    public void setCourseNewRecommendations(Integer courseNewRecommendations) {
        this.courseNewRecommendations = courseNewRecommendations;
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

    public Long getCareerPathId() {
        return careerPathId;
    }

    public void setCareerPathId(Long careerPathId) {
        this.careerPathId = careerPathId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if(courseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
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
