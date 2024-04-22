package com.topcv.model;

/**
 * This class represents a response message.
 */
public class ResponseMessage {
    private String message;
    private String systemMessage;
    private boolean success = true;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public void SetSuccess(String message) {
        this.message = message;
        this.success = true;
    }

    public void SetError(String message) {
        this.message = message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }
}
