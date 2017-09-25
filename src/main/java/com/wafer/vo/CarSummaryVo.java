package com.wafer.vo;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by liupc
 */

public class CarSummaryVo {

  private long id;

  private String carNum;

  private String operateNum;

  private String ownerName;

  private String ownerPhone;

  private Date forceInsure;

  private Date busInsure;

  private Integer outBuy;

  private Integer hasReceive;

  private Integer hasPay;

  private Date operateDate;

  private Date gpsDate;

  private BigInteger violateNum;

  private Date examDate;

  private Date manageDate;

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

  public Integer getOutBuy() {
    return outBuy;
  }

  public void setOutBuy(Integer outBuy) {
    this.outBuy = outBuy;
  }

  public Integer getHasReceive() {
    return hasReceive;
  }

  public void setHasReceive(Integer hasReceive) {
    this.hasReceive = hasReceive;
  }

  public Integer getHasPay() {
    return hasPay;
  }

  public void setHasPay(Integer hasPay) {
    this.hasPay = hasPay;
  }

  public Date getOperateDate() {
    return operateDate;
  }

  public void setOperateDate(Date operateDate) {
    this.operateDate = operateDate;
  }

  public Date getGpsDate() {
    return gpsDate;
  }

  public void setGpsDate(Date gpsDate) {
    this.gpsDate = gpsDate;
  }

  public BigInteger getViolateNum() {
    return violateNum;
  }

  public void setViolateNum(BigInteger violateNum) {
    this.violateNum = violateNum;
  }

  public Date getExamDate() {
    return examDate;
  }

  public void setExamDate(Date examDate) {
    this.examDate = examDate;
  }

  public Date getManageDate() {
    return manageDate;
  }

  public void setManageDate(Date manageDate) {
    this.manageDate = manageDate;
  }

  public CarSummaryVo() {
    super();
  }

  public CarSummaryVo(long id, String carNum, String operateNum, String ownerName,
      String ownerPhone, Date forceInsure, Date busInsure, Integer outBuy, Integer hasReceive,
      Integer hasPay, Date operateDate, Date gpsDate, BigInteger violateNum, Date examDate,
      Date manageDate) {
    super();
    this.id = id;
    this.carNum = carNum;
    this.operateNum = operateNum;
    this.ownerName = ownerName;
    this.ownerPhone = ownerPhone;
    this.forceInsure = forceInsure;
    this.busInsure = busInsure;
    this.outBuy = outBuy;
    this.hasReceive = hasReceive;
    this.hasPay = hasPay;
    this.operateDate = operateDate;
    this.gpsDate = gpsDate;
    this.violateNum = violateNum;
    this.examDate = examDate;
    this.manageDate = manageDate;
  }

}
