package com.wafer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * Created by liupc
 */

@Entity
@Table(name = "ps_car")
public class Car {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  @Column(name = "car_num")
  private String carNum;

  @Column(name = "operate_num")
  private String operateNum;
  
  @Column(name = "owner_name")
  private String ownerName;
  
  @Column(name = "owner_phone")
  private String ownerPhone;
  
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

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getOwnerPhone() {
    return ownerPhone;
  }

  public void setOwnerPhone(String ownerPhone) {
    this.ownerPhone = ownerPhone;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Car() {
    super();
  }

  public Car(long id, String carNum, String operateNum, String ownerName, 
      String ownerPhone, Date updateTime) {
    super();
    this.id = id;
    this.carNum = carNum;
    this.operateNum = operateNum;
    this.ownerName = ownerName;
    this.ownerPhone = ownerPhone;
    this.updateTime = updateTime;
  }
}
