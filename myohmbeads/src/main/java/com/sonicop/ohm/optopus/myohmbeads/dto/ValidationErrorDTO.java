package com.sonicop.ohm.optopus.myohmbeads.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {

  private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

  public ValidationErrorDTO() {

  }

  public void addFieldError(String path, String message) {
    FieldErrorDTO error = new FieldErrorDTO(path, message);
    fieldErrors.add(error);
  }

  public List<FieldErrorDTO> getFieldErrors() {
    return fieldErrors;
  }
}
