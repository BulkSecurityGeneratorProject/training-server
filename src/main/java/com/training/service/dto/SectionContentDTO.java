package com.training.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SectionContent entity.
 */
public class SectionContentDTO implements Serializable {

    private Long id;

    private Integer sectionTime;

    private String sectionTitle;

    private String sectionContent;

    private String reservedOne;

    private String reservedTwo;

    private Long courseSectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(Integer sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionContent() {
        return sectionContent;
    }

    public void setSectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
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

    public Long getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(Long courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SectionContentDTO sectionContentDTO = (SectionContentDTO) o;
        if(sectionContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectionContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectionContentDTO{" +
            "id=" + getId() +
            ", sectionTime=" + getSectionTime() +
            ", sectionTitle='" + getSectionTitle() + "'" +
            ", sectionContent='" + getSectionContent() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
