package com.sonicop.ohm.optopus.myohmbeads.dto;

import java.util.Date;

public class PurchaseTransaction {

  private String transactionId;
  private String brandId;
  private String sku;
  private String productName;
  private String userId;
  private Date createTime;
  private String currencyCode;
  private String purchasePrice;
  private String purchaseFrom;
  private String purchaseDate;
  private String note;
  private String imageReference;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(String purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public String getPurchaseFrom() {
    return purchaseFrom;
  }

  public void setPurchaseFrom(String purchaseFrom) {
    this.purchaseFrom = purchaseFrom;
  }

  public String getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(String purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
  
  public String getImageReference() {
    return imageReference;
  }

  public void setImageReference(String imageReference) {
    this.imageReference = imageReference;
  }
}
