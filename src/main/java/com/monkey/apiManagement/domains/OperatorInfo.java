package com.monkey.apiManagement.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(schema = "dbo", name = "api_operator_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OperatorInfo implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "operator_id")
    private Integer id;

    @Column(name = "operator_name")
    private String name;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "operator_identifier")
    private String identifier;

    @Column(name = "modifieddate")
    private Timestamp modifiedDate;

    @Column(name = "dsn_name")
    private String dsnName;

    @Column(name = "cfurl")
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
        OperatorInfo that = (OperatorInfo) o;
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
        final StringBuilder sb = new StringBuilder("OperatorInfo{");
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
