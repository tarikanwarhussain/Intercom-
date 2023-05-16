package com.trantor.exception;

import com.trantor.bo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex.getLocalizedMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<ErrorResponse> handleHttpClientErrorException(
      HttpClientErrorException ex, WebRequest request) {
    ErrorResponse error = prepareErrorResponse(ex);
    return new ResponseEntity<>(error, ex.getStatusCode());
  }

  /**
   * Prepare the ErrorResponse object from the HttpClientErrorException.
   *
   * @param ex
   * @return
   */
  private ErrorResponse prepareErrorResponse(HttpClientErrorException ex) {

    String errorMessage = ex.getMessage();
    if (HttpStatus.UNAUTHORIZED.value() == ex.getStatusCode().value()) {
      errorMessage = "Access token expired. Please get new access token from refresh token";
    }
    return new ErrorResponse(ex.getStatusCode().value(), ex.getStatusText(), errorMessage);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMissingParams(
      MissingServletRequestParameterException ex) {
    String name = ex.getParameterName();
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), "BAD Request", "Missing required parameter: " + name);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMissingRequestHeader(
      MissingRequestHeaderException ex) {
    String name = ex.getHeaderName();
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "BAD Request",
            "Missing required request header: " + name);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
