package com.wafer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
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

  @Column(name = "car_num")
  private String carNum;

  @Column(name = "operate_num")
  private String operateNum;

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
  private String updateUser;

  @Column(name = "update_time")
  private Date updateTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCarNum() {
    return carNum;
  }

  public void setCarNum(String carNum) {
    this.carNum = carNum;
  }

  public String getOperateNum() {
    return operateNum;
  }

  public void setOperateNum(String operateNum) {
    this.operateNum = operateNum;
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

  public String getUpdateUser() {
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

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Insure() {
    super();
  }

  public Insure(long id, String carNum, String operateNum, Date forceInsure, Date busInsure,
      int outBuy, int hasReceive, int hasPay, String updateUser, Date updateTime) {
    super();
    this.id = id;
    this.carNum = carNum;
    this.operateNum = operateNum;
    this.forceInsure = forceInsure;
    this.busInsure = busInsure;
    this.outBuy = outBuy;
    this.hasReceive = hasReceive;
    this.hasPay = hasPay;
    this.updateUser = updateUser;
    this.updateTime = updateTime;
  }
}
