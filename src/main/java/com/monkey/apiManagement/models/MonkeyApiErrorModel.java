package com.monkey.apiManagement.models;

import java.io.Serializable;
import java.util.Objects;

public class MonkeyApiErrorModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer errorCode;

    private int responseStatus;

    private String errorMessage;

    private String developerMessage;

    private String additionalInfo;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonkeyApiErrorModel that = (MonkeyApiErrorModel) o;
        return errorCode == that.errorCode &&
                responseStatus == that.responseStatus &&
                Objects.equals(errorMessage, that.errorMessage) &&
                Objects.equals(developerMessage, that.developerMessage) &&
                Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, responseStatus, errorMessage, developerMessage, additionalInfo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonkeyApiErrorModel{");
        sb.append("errorCode=").append(errorCode);
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", developerMessage='").append(developerMessage).append('\'');
        sb.append(", additionalInfo='").append(additionalInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
