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
@Table(name = "ps_violate")
public class Violate {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  @Column(name = "car_id")
  private long carId;

  @Column(name = "record_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date recordDate;

  @Column(name = "has_deal")
  private int hasDeal;
  
  @Column(name = "remark")
  private String remark;

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

  public Date getRecordDate() {
    return recordDate;
  }

  public void setRecordDate(Date recordDate) {
    this.recordDate = recordDate;
  }

  public int getHasDeal() {
    return hasDeal;
  }

  public void setHasDeal(int hasDeal) {
    this.hasDeal = hasDeal;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public Violate() {
    super();
  }

  public Violate(long id, long carId, Date recordDate, int hasDeal, String remark, long updateUser,
      Date updateTime) {
    super();
    this.id = id;
    this.carId = carId;
    this.recordDate = recordDate;
    this.hasDeal = hasDeal;
    this.remark = remark;
    this.updateUser = updateUser;
    this.updateTime = updateTime;
  }
}
