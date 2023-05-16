package com.trantor.bo;

public class ErrorResponse {
  private String errorMessage;
  private int statusCode;
  private String errorDescription;

  public ErrorResponse(int statusCode, String errorMessage, String errorDesc) {
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
    this.errorDescription = errorDesc;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }
}
