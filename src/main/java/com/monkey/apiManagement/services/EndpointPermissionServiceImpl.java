package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.ApiAuth;
import com.monkey.apiManagement.domains.EndpointPermission;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.ApiAuthRepository;
import com.monkey.apiManagement.repositories.EndpointPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndpointPermissionServiceImpl implements EndpointPermissionService {

    @Autowired
    private EndpointPermissionRepository endpointPermissionRepository;

    @Autowired
    private ApiAuthRepository apiAuthRepository;

    @Override
    public EndpointPermission saveAndFlush(EndpointPermission endpointPermission) {
        EndpointPermission result = null;
        ApiAuth apiAuth = apiAuthRepository.getOne(endpointPermission.getApiAuthId());
        if (apiAuth == null) {
            throw new NotFoundException(
                    new StringBuilder("Api auth with id: ").append(endpointPermission.getApiAuthId())
                            .append(", not found.").toString()
            );
        }
        result = endpointPermissionRepository.saveAndFlush(endpointPermission);
        return result;
    }

    @Override
    public EndpointPermission updateAndFlush(int id, EndpointPermission endpointPermission) {
        EndpointPermission result = null;
        EndpointPermission retrievedEndpointPermission = endpointPermissionRepository.getOne(id);
        if (retrievedEndpointPermission == null) {
            throw new NotFoundException(
                    new StringBuilder("Endpoint Permission with id: ").append(id).append(", not found.").toString()
            );
        } else {
            if (endpointPermission.getId() != null) {
                throw new ReadOnlyException("Id is read-only.");
            }
            if (endpointPermission.getApiAuthId() != null) {
                retrievedEndpointPermission.setApiAuthId(endpointPermission.getApiAuthId());
            }
            if (endpointPermission.getEndPoint() != null) {
                retrievedEndpointPermission.setEndPoint(endpointPermission.getEndPoint());
            }
            retrievedEndpointPermission.setActive(endpointPermission.isActive());
        }
        result = endpointPermissionRepository.saveAndFlush(retrievedEndpointPermission);
        return result;
    }
}
