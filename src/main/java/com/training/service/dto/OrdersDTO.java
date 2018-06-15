package com.training.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Orders entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    private Integer orderNumber;

    private ZonedDateTime dateOfPayment;

    private Integer payTheAmount;

    private String theDrawee;

    private String payForCourses;

    private String reservedOne;

    private String reservedTwo;

    private Long courseId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ZonedDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(ZonedDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public Integer getPayTheAmount() {
        return payTheAmount;
    }

    public void setPayTheAmount(Integer payTheAmount) {
        this.payTheAmount = payTheAmount;
    }

    public String getTheDrawee() {
        return theDrawee;
    }

    public void setTheDrawee(String theDrawee) {
        this.theDrawee = theDrawee;
    }

    public String getPayForCourses() {
        return payForCourses;
    }

    public void setPayForCourses(String payForCourses) {
        this.payForCourses = payForCourses;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if(ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", orderNumber=" + getOrderNumber() +
            ", dateOfPayment='" + getDateOfPayment() + "'" +
            ", payTheAmount=" + getPayTheAmount() +
            ", theDrawee='" + getTheDrawee() + "'" +
            ", payForCourses='" + getPayForCourses() + "'" +
            ", reservedOne='" + getReservedOne() + "'" +
            ", reservedTwo='" + getReservedTwo() + "'" +
            "}";
    }
}
