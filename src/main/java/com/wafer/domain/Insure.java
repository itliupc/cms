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
@Table(name = "ps_insure")
public class Insure {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "id", unique = true, nullable = false)
  private long id;
  
  @Column(name = "car_id")
  private long carId;
  
  @Column(name = "force_insure")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date forceInsure;

  @Column(name = "bus_insure")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date busInsure;

  @Column(name = "out_buy")
  private int outBuy;

  @Column(name = "has_receive")
  private int hasReceive;

  @Column(name = "has_pay")
  private int hasPay;

  @Column(name = "update_user")
  private long updateUser;

  @Column(name = "update_time")
  private Date updateTime;
  
  @OneToOne
  @NotFound(action=NotFoundAction.IGNORE)
  @JoinColumn(name = "update_user", nullable=true, insertable=false, updatable=false)
  private User user;
  
  @OneToOne
  @NotFound(action=NotFoundAction.IGNORE)
  @JoinColumn(name = "car_id", nullable=true, insertable=false, updatable=false)
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

  public Date getForceInsure() {
    return forceInsure;
  }

  public void setForceInsure(Date forceInsure) {
    this.forceInsure = forceInsure;
  }

  public Date getBusInsure() {
    return busInsure;
  }

  public void setBusInsure(Date busInsure) {
    this.busInsure = busInsure;
  }

  public long getUpdateUser() {
    return updateUser;
  }

  public int getOutBuy() {
    return outBuy;
  }

  public void setOutBuy(int outBuy) {
    this.outBuy = outBuy;
  }

  public int getHasReceive() {
    return hasReceive;
  }

  public void setHasReceive(int hasReceive) {
    this.hasReceive = hasReceive;
  }

  public int getHasPay() {
    return hasPay;
  }

  public void setHasPay(int hasPay) {
    this.hasPay = hasPay;
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

  public Insure() {
    super();
  }

  public Insure(long id, long carId, Date forceInsure, Date busInsure, 
      int outBuy, int hasReceive, int hasPay, long updateUser, 
      Date updateTime) {
    super();
    this.id = id;
    this.carId = carId;
    this.forceInsure = forceInsure;
    this.busInsure = busInsure;
    this.outBuy = outBuy;
    this.hasReceive = hasReceive;
    this.hasPay = hasPay;
    this.updateUser = updateUser;
    this.updateTime = updateTime;
  }
}
