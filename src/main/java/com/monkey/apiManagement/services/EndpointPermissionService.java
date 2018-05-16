package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.EndpointPermission;

public interface EndpointPermissionService {
    EndpointPermission saveAndFlush(EndpointPermission endpointPermission);
    EndpointPermission updateAndFlush(int id, EndpointPermission endpointPermission);
}
