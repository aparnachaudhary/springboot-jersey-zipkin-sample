package io.github.aparnachaudhary.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestErrorEntity {

    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("user_message")
    private String userMessage;
    @JsonProperty("developer_message")
    private String developerMessage;

    public RestErrorEntity(int errorCode, String userMessage, String developerMessage) {
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
