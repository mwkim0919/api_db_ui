package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.EndpointPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndpointPermissionRepository extends JpaRepository<EndpointPermission, Integer>,
        JpaSpecificationExecutor<EndpointPermission> {

    List<EndpointPermission> getEndpointPermissionsByApiAuthId(@Param("apiAuthId") int id);

    String GET_PERMISSIONS_BY_API_AUTH_ID = "SELECT ep" +
            " FROM EndpointPermission ep JOIN FETCH ep.endPoint " +
            " WHERE ep.apiAuthId = :apiAuthId";

    @Query(GET_PERMISSIONS_BY_API_AUTH_ID)
    List<EndpointPermission> getEndpointPermissionsByApiAuthIdV2(@Param("apiAuthId") int id);

//    String INSERT_ALL_ENDPOINTS_FOR_API_AUTH_ID = "INSERT INTO EndpointPermission(apiAuthId, endpoint, isActive) " +
//                    "SELECT :apiAuthId, ep.id, 0 FROM Endpoint ep";
//    @Modifying
//    @Query(INSERT_ALL_ENDPOINTS_FOR_API_AUTH_ID)
//    List<EndpointPermission> insertAllEndpointsForApiAuth(@Param("apiAuthId") int id);
}
