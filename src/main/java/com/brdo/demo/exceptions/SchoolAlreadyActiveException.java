package com.brdo.demo.exceptions;

public class SchoolAlreadyActiveException extends RuntimeException {
  public SchoolAlreadyActiveException(String message) {
    super(message);
  }
}
