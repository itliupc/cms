package com.wafer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * Created by liupc
 */

@Entity
@Table(name = "ps_exam")
public class Exam {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  @Column(name = "car_id")
  private long carId;

  @Column(name = "end_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;

  @Column(name = "update_user")
  private long updateUser;

  @Column(name = "update_time")
  private Date updateTime;

  @OneToOne
  @NotFound(action = NotFoundAction.IGNORE)
  @JoinColumn(name = "update_user", nullable = true, insertable = false, updatable = false)
  private User user;

  @OneToOne
  @NotFound(action = NotFoundAction.IGNORE)
  @JoinColumn(name = "car_id", nullable = true, insertable = false, updatable = false)
  private Car car;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCarId() {
    return carId;
  }

  public void setCarId(long carId) {
    this.carId = carId;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public long getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(long updateUser) {
    this.updateUser = updateUser;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public Exam() {
    super();
  }

  public Exam(long id, long carId, Date endDate, long updateUser, Date updateTime) {
    super();
    this.id = id;
    this.carId = carId;
    this.endDate = endDate;
    this.updateUser = updateUser;
    this.updateTime = updateTime;
  }
}
