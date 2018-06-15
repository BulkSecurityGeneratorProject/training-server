package com.training.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Forum entity.
 */
public class ForumDTO implements Serializable {

    private Long id;

    private String forumName;

    private String forumImg;

    private String forunIntroduce;

    private String reservedOne;

    private String reservedTwo;

    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumImg() {
        return forumImg;
    }

    public void setForumImg(String forumImg) {
        this.forumImg = forumImg;
    }

    public String getForunIntroduce() {
        return forunIntroduce;
    }

    public void setForunIntroduce(String forunIntroduce) {
        this.forunIntroduce = forunIntroduce;
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

        ForumDTO forumDTO = (ForumDTO) o;
        if(forumDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), forumDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ForumDTO{" +
            "id=" + getId() +
            ", forumName='" + getForumName() + "'" +
            ", forumImg='" + getForumImg() + "'" +
            ", forunIntroduce='" + getForunIntroduce() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
