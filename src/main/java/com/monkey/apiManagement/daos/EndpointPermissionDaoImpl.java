package com.monkey.apiManagement.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@Transactional
public class EndpointPermissionDaoImpl implements EndpointPermissionDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean insertPermissionsForAuth(int authId) {
        String qryString = "INSERT INTO dbo.access_to_endpoints(api_key_id, endpoint_id, isActive) SELECT :authId, endpoint_id, 1 FROM dbo.endpoints";
        Query query  = entityManager.createNativeQuery(qryString).setParameter("authId", authId);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteAllPermissionsForAuth(int authId) {
        String qryString = "DELETE FROM EndpointPermission WHERE apiAuthId = :authId";
        Query query  = entityManager.createQuery(qryString).setParameter("authId", authId);
        return query.executeUpdate() > 0;
    }
}
