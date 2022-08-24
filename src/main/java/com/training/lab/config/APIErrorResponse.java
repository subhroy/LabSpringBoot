package com.training.lab.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIErrorResponse {

  private String message;
  private Object cause;
  private Object httpStatusCode;
  private boolean success = false;
}
