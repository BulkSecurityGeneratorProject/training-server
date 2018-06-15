package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SectionContent.
 */
@Entity
@Table(name = "section_content")
public class SectionContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_time")
    private Integer sectionTime;

    @Column(name = "section_title")
    private String sectionTitle;

    @Column(name = "section_content")
    private String sectionContent;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private CourseSection courseSection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSectionTime() {
        return sectionTime;
    }

    public SectionContent sectionTime(Integer sectionTime) {
        this.sectionTime = sectionTime;
        return this;
    }

    public void setSectionTime(Integer sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public SectionContent sectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
        return this;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionContent() {
        return sectionContent;
    }

    public SectionContent sectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
        return this;
    }

    public void setSectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public SectionContent reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public SectionContent reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public CourseSection getCourseSection() {
        return courseSection;
    }

    public SectionContent courseSection(CourseSection courseSection) {
        this.courseSection = courseSection;
        return this;
    }

    public void setCourseSection(CourseSection courseSection) {
        this.courseSection = courseSection;
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
        SectionContent sectionContent = (SectionContent) o;
        if (sectionContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectionContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectionContent{" +
            "id=" + getId() +
            ", sectionTime=" + getSectionTime() +
            ", sectionTitle='" + getSectionTitle() + "'" +
            ", sectionContent='" + getSectionContent() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
