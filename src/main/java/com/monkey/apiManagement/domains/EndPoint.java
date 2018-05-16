package com.monkey.apiManagement.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "dbo", name = "endpoints")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EndPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endpoint_id")
    private Integer id;

    @Column(name = "endpoint_name")
    private String name;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, orphanRemoval=true, mappedBy = "endPoint")
    private List<EndpointPermission> permissions;

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
        EndPoint endPoint = (EndPoint) o;
        return id == endPoint.id &&
                Objects.equals(name, endPoint.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EndPoint{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
