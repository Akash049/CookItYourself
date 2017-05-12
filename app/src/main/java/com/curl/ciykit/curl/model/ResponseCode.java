package com.curl.ciykit.curl.model;

/**
 * Created by Akash Chandra on 08-02-2017.
 */

public class ResponseCode {
    public String status;
    public String message;

    public ResponseCode() {
    }

    public ResponseCode(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
