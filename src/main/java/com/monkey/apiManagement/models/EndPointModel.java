package com.monkey.apiManagement.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EndPointModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private List<EndpointPermissionModel> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndPointModel endPointModel = (EndPointModel) o;
        return id == endPointModel.id &&
                Objects.equals(name, endPointModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EndPointModel{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
