package com.monkey.apiManagement.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "dbo", name = "access_to_endpoints")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EndpointPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "api_key_id")
    private Integer apiAuthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endpoint_id", referencedColumnName = "endpoint_id")
    private EndPoint endPoint;

    @Column(name = "isActive")
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

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
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
        EndpointPermission that = (EndpointPermission) o;
        return id == that.id &&
                Objects.equals(apiAuthId, that.apiAuthId) &&
                Objects.equals(endPoint, that.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiAuthId, endPoint);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EndpointPermission{");
        sb.append("id=").append(id);
        sb.append(", apiAuthId=").append(apiAuthId);
        sb.append(", endPoint=").append(endPoint);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
