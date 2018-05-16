package com.monkey.apiManagement.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "dbo", name = "monkeyapi_errors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MonkeyApiError implements Serializable {

    @Id
    @Column(name = "error_code")
    private Integer errorCode;

    @Column(name = "response_status")
    private int responseStatus;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "developer_message")
    private String developerMessage;

    @Column(name = "more_info")
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
        MonkeyApiError that = (MonkeyApiError) o;
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
        final StringBuilder sb = new StringBuilder("MonkeyApiError{");
        sb.append("errorCode=").append(errorCode);
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", developerMessage='").append(developerMessage).append('\'');
        sb.append(", additionalInfo='").append(additionalInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
