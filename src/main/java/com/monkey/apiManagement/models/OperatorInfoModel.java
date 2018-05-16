package com.monkey.apiManagement.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class OperatorInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Timestamp createDate;

    private String identifier;

    private Timestamp modifiedDate;

    private String dsnName;

    private String cfUrl;

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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDsnName() {
        return dsnName;
    }

    public void setDsnName(String dsnName) {
        this.dsnName = dsnName;
    }

    public String getCfUrl() {
        return cfUrl;
    }

    public void setCfUrl(String cfUrl) {
        this.cfUrl = cfUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorInfoModel that = (OperatorInfoModel) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(modifiedDate, that.modifiedDate) &&
                Objects.equals(dsnName, that.dsnName) &&
                Objects.equals(cfUrl, that.cfUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, identifier, modifiedDate, dsnName, cfUrl);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OperatorInfoModel{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", identifier='").append(identifier).append('\'');
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append(", dsnName='").append(dsnName).append('\'');
        sb.append(", cfUrl='").append(cfUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
