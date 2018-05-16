package com.monkey.apiManagement.models;

import java.io.Serializable;
import java.util.Objects;

public class EndpointPermissionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer apiAuthId;

    private EndPointModel endPointModel;

    private boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApiAuthId() {
        return apiAuthId;
    }

    public void setApiAuthId(Integer apiAuthId) {
        this.apiAuthId = apiAuthId;
    }

    public EndPointModel getEndPoint() {
        return endPointModel;
    }

    public void setEndPoint(EndPointModel endPointModel) {
        this.endPointModel = endPointModel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointPermissionModel that = (EndpointPermissionModel) o;
        return id == that.id &&
                Objects.equals(apiAuthId, that.apiAuthId) &&
                Objects.equals(endPointModel, that.endPointModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiAuthId, endPointModel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EndpointPermissionModel{");
        sb.append("id=").append(id);
        sb.append(", apiAuthId=").append(apiAuthId);
        sb.append(", endPointModel=").append(endPointModel);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
