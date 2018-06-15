package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CareerPath.
 */
@Entity
@Table(name = "career_path")
public class CareerPath implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "career_path_name")
    private String careerPathName;

    @Column(name = "career_path_introduce")
    private String careerPathIntroduce;

    @Column(name = "career_path_img")
    private String careerPathImg;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerPathName() {
        return careerPathName;
    }

    public CareerPath careerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
        return this;
    }

    public void setCareerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
    }

    public String getCareerPathIntroduce() {
        return careerPathIntroduce;
    }

    public CareerPath careerPathIntroduce(String careerPathIntroduce) {
        this.careerPathIntroduce = careerPathIntroduce;
        return this;
    }

    public void setCareerPathIntroduce(String careerPathIntroduce) {
        this.careerPathIntroduce = careerPathIntroduce;
    }

    public String getCareerPathImg() {
        return careerPathImg;
    }

    public CareerPath careerPathImg(String careerPathImg) {
        this.careerPathImg = careerPathImg;
        return this;
    }

    public void setCareerPathImg(String careerPathImg) {
        this.careerPathImg = careerPathImg;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public CareerPath reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public CareerPath reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
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
        CareerPath careerPath = (CareerPath) o;
        if (careerPath.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), careerPath.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CareerPath{" +
            "id=" + getId() +
            ", careerPathName='" + getCareerPathName() + "'" +
            ", careerPathIntroduce='" + getCareerPathIntroduce() + "'" +
            ", careerPathImg='" + getCareerPathImg() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
