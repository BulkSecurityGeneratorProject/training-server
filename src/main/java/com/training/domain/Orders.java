package com.training.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "date_of_payment")
    private ZonedDateTime dateOfPayment;

    @Column(name = "pay_the_amount")
    private Integer payTheAmount;

    @Column(name = "the_drawee")
    private String theDrawee;

    @Column(name = "pay_for_courses")
    private String payForCourses;

    @Column(name = "reserved_one")
    private String reservedOne;

    @Column(name = "reserved_two")
    private String reservedTwo;

    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public Orders orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ZonedDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public Orders dateOfPayment(ZonedDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
        return this;
    }

    public void setDateOfPayment(ZonedDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public Integer getPayTheAmount() {
        return payTheAmount;
    }

    public Orders payTheAmount(Integer payTheAmount) {
        this.payTheAmount = payTheAmount;
        return this;
    }

    public void setPayTheAmount(Integer payTheAmount) {
        this.payTheAmount = payTheAmount;
    }

    public String getTheDrawee() {
        return theDrawee;
    }

    public Orders theDrawee(String theDrawee) {
        this.theDrawee = theDrawee;
        return this;
    }

    public void setTheDrawee(String theDrawee) {
        this.theDrawee = theDrawee;
    }

    public String getPayForCourses() {
        return payForCourses;
    }

    public Orders payForCourses(String payForCourses) {
        this.payForCourses = payForCourses;
        return this;
    }

    public void setPayForCourses(String payForCourses) {
        this.payForCourses = payForCourses;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public Orders reservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
        return this;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public Orders reservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
        return this;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public Course getCourse() {
        return course;
    }

    public Orders course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public Orders user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Orders orders = (Orders) o;
        if (orders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
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
