package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.EndPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndPointRepository extends JpaRepository<EndPoint, Integer>, JpaSpecificationExecutor<EndPoint> {
    String GET_ADDABLE_ENDPONTS = "SELECT e " +
            "FROM EndPoint e " +
            "WHERE e.id not in (select ep.endPoint.id from EndpointPermission ep where ep.apiAuthId = :apiAuthId)";
    @Query(GET_ADDABLE_ENDPONTS)
    List<EndPoint> getAddabledEndPointsByApiAuthId(@Param("apiAuthId") int apiAuthId);

    List<EndPoint> findAllByOrderByName();
}
