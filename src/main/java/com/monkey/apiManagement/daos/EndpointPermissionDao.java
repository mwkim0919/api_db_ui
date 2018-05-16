package com.monkey.apiManagement.daos;

public interface EndpointPermissionDao {
    boolean insertPermissionsForAuth(int apiAuthId);

    boolean deleteAllPermissionsForAuth(int authId);
}
