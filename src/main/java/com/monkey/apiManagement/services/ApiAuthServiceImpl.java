package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.ApiAuth;
import com.monkey.apiManagement.domains.OperatorInfo;
import com.monkey.apiManagement.domains.VendorInfo;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.ApiAuthRepository;
import com.monkey.apiManagement.repositories.OperatorInfoRepository;
import com.monkey.apiManagement.repositories.VendorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiAuthServiceImpl implements ApiAuthService {

    @Autowired
    private ApiAuthRepository apiAuthRepository;

    @Autowired
    private OperatorInfoRepository operatorInfoRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Override
    public ApiAuth saveAndFlush(ApiAuth apiAuth) {
        apiAuth.setClientSecret(UUID.randomUUID().toString().replace("-",""));
        ApiAuth result = apiAuthRepository.saveAndFlush(apiAuth);
        return result;
    }

    @Override
    public ApiAuth updateAndFlush(int id, ApiAuth apiAuth) {
        ApiAuth result = null;
        ApiAuth retrievedApiAuth = apiAuthRepository.getOne(id);
        if (retrievedApiAuth == null) {
            throw new NotFoundException(
                    new StringBuilder("Api auth with id: ").append(id).append(", not found.").toString()
            );
        } else {
            if (apiAuth.getId() != null) {
                throw new ReadOnlyException("Id is read-only.");
            }
            if (apiAuth.getApiKey() != null) {
                retrievedApiAuth.setApiKey(apiAuth.getApiKey());
            }
            if (apiAuth.getClientSecret() != null) {
                retrievedApiAuth.setClientSecret(apiAuth.getClientSecret());
            }
            if (apiAuth.getResourceIds() != null) {
                retrievedApiAuth.setResourceIds(apiAuth.getResourceIds());
            }
            if (apiAuth.getScope() != null) {
                retrievedApiAuth.setScope(apiAuth.getScope());
            }
            if (apiAuth.getAuthorizedGrantTypes() != null) {
                retrievedApiAuth.setAuthorizedGrantTypes(apiAuth.getAuthorizedGrantTypes());
            }
            if (apiAuth.getWebServerRedirectUri() != null) {
                retrievedApiAuth.setWebServerRedirectUri(apiAuth.getWebServerRedirectUri());
            }
            if (apiAuth.getAuthorities() != null) {
                retrievedApiAuth.setAuthorities(apiAuth.getAuthorities());
            }
            if (apiAuth.getAccessTokenValidity() != null) {
                retrievedApiAuth.setAccessTokenValidity(apiAuth.getAccessTokenValidity());
            }
            if (apiAuth.getRefreshTokenValidity() != null) {
                retrievedApiAuth.setRefreshTokenValidity(apiAuth.getRefreshTokenValidity());
            }
            if (apiAuth.getAdditionalInformation() != null) {
                retrievedApiAuth.setAdditionalInformation(apiAuth.getAdditionalInformation());
            }
            if (apiAuth.getAutoApprove() != null) {
                retrievedApiAuth.setAutoApprove(apiAuth.getAutoApprove());
            }
            OperatorInfo operatorInfo = operatorInfoRepository.getOne(apiAuth.getOperatorId());
            if (operatorInfo == null) {
                throw new NotFoundException(
                        new StringBuilder("Operator info with id: ").append(id).append(", not found.").toString()
                );
            } else {
                retrievedApiAuth.setOperatorId(operatorInfo.getId());
            }
            if (apiAuth.getVendorId() != null) {
                VendorInfo vendorInfo = vendorInfoRepository.getOne(apiAuth.getVendorId());
                if (vendorInfo == null) {
                    throw new NotFoundException(
                            new StringBuilder("Operator info with id: ").append(id).append(", not found.").toString()
                    );
                } else {
                    retrievedApiAuth.setVendorId(vendorInfo.getId());
                }
            }
        }
        result = apiAuthRepository.saveAndFlush(retrievedApiAuth);
        return result;
    }
}
