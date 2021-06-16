package com.restapikarkinos.WebappApi.validation;

import java.util.Date;

public class ErrorDetails {
    
    private Date timestamp;
    private String message;
    private String details;
    private Boolean status;

    public ErrorDetails(Date timestamp, String message, String details, Boolean status) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
