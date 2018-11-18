package com.sonicop.ohm.optopus.myohmbeads.dto;

public class ImageMetaData {
  private Integer sortNumber;
  private String reference;
  private String caption;
  private String originalFileName;
  private String takenByMake;
  private String takenByModel;  

  public Integer getSortNumber() {
    return sortNumber;
  }

  public void setSortNumber(Integer sortNumber) {
    this.sortNumber = sortNumber;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getTakenByMake() {
    return takenByMake;
  }

  public void setTakenByMake(String takenByMake) {
    this.takenByMake = takenByMake;
  }

  public String getTakenByModel() {
    return takenByModel;
  }

  public void setTakenByModel(String takenByModel) {
    this.takenByModel = takenByModel;
  }
  

}
