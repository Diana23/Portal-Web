package com.cablevision.exception;

/**
 * An exception class for when there's a problem handling SAML
 * messages.
 */
public class SamlException extends Exception {

  protected String message = "";

  public SamlException() {
  }
  
  public SamlException(Throwable e) {
    super(e);
  }

  public SamlException(String message) {
    this.message = message;
  }
  
  public String getMessage() {
    return this.message;
  }

  public String toString() {
    return "SAML exception: " + message;
  }
}