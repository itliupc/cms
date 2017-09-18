package com.wafer.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;



/**
 * Created by liupc
 */

public class InsureVo {

  private long id;

  private long carId;

  private String carNum;

  private String operateNum;

  private String ownerName;

  private String ownerPhone;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date forceInsure;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date busInsure;

  private int outBuy;

  private int hasReceive;

  private int hasPay;

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


  public InsureVo() {
    super();
  }

  public InsureVo(long id, long carId, String carNum, String operateNum, String ownerName,
      String ownerPhone, Date forceInsure, Date busInsure, int outBuy, int hasReceive, int hasPay) {
    super();
    this.id = id;
    this.carId = carId;
    this.carNum = carNum;
    this.operateNum = operateNum;
    this.ownerName = ownerName;
    this.ownerPhone = ownerPhone;
    this.forceInsure = forceInsure;
    this.busInsure = busInsure;
    this.outBuy = outBuy;
    this.hasReceive = hasReceive;
    this.hasPay = hasPay;
  }
}
