package com.sonicop.ohm.optopus.myohmbeads.dto;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PurchaseTransaction {

  private String transactionId;
  
  @NotEmpty(message = "Product SKU is required.")
  @Size(max = 48, message="Product SKU is invalid")
  private String sku;
  
  private String productName;
  
  private Date createTime;
  
  @Size(max = 3, message="Currency code is invalid.")
  private String currencyCode;
  
  @Pattern(regexp="^(\\s*|\\d*\\.?\\d+)$", message="Purchase price is invalid format.")
  @Size(max = 13, message="Purchase price exceed maximum length.")
  private String purchasePrice;
  
  @Size(max = 100, message="Purchase from is invalid")
  private String purchaseFrom;
  
  private String purchaseDate;
  
  @Size(max = 500, message="Note is invalid")
  private String note;
  private String imageReference;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
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
