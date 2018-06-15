package com.training.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CareerPath entity.
 */
public class CareerPathDTO implements Serializable {

    private Long id;

    private String careerPathName;

    private String careerPathIntroduce;

    private String careerPathImg;

    private String reservedOne;

    private String reservedTwo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerPathName() {
        return careerPathName;
    }

    public void setCareerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
    }

    public String getCareerPathIntroduce() {
        return careerPathIntroduce;
    }

    public void setCareerPathIntroduce(String careerPathIntroduce) {
        this.careerPathIntroduce = careerPathIntroduce;
    }

    public String getCareerPathImg() {
        return careerPathImg;
    }

    public void setCareerPathImg(String careerPathImg) {
        this.careerPathImg = careerPathImg;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CareerPathDTO careerPathDTO = (CareerPathDTO) o;
        if(careerPathDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), careerPathDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CareerPathDTO{" +
            "id=" + getId() +
            ", careerPathName='" + getCareerPathName() + "'" +
            ", careerPathIntroduce='" + getCareerPathIntroduce() + "'" +
            ", careerPathImg='" + getCareerPathImg() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
