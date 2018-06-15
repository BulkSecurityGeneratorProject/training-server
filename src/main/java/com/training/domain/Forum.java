package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Forum.
 */
@Entity
@Table(name = "forum")
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "forum_name")
    private String forumName;

    @Column(name = "forum_img")
    private String forumImg;

    @Column(name = "forun_introduce")
    private String forunIntroduce;

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

    public String getForumName() {
        return forumName;
    }

    public Forum forumName(String forumName) {
        this.forumName = forumName;
        return this;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumImg() {
        return forumImg;
    }

    public Forum forumImg(String forumImg) {
        this.forumImg = forumImg;
        return this;
    }

    public void setForumImg(String forumImg) {
        this.forumImg = forumImg;
    }

    public String getForunIntroduce() {
        return forunIntroduce;
    }

    public Forum forunIntroduce(String forunIntroduce) {
        this.forunIntroduce = forunIntroduce;
        return this;
    }

    public void setForunIntroduce(String forunIntroduce) {
        this.forunIntroduce = forunIntroduce;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public Forum reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public Forum reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public Course getCourse() {
        return course;
    }

    public Forum course(Course course) {
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
        Forum forum = (Forum) o;
        if (forum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), forum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Forum{" +
            "id=" + getId() +
            ", forumName='" + getForumName() + "'" +
            ", forumImg='" + getForumImg() + "'" +
            ", forunIntroduce='" + getForunIntroduce() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
