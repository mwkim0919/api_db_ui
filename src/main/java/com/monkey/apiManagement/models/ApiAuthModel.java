package com.monkey.apiManagement.models;

import com.monkey.apiManagement.domains.ApiAuth;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ApiAuthModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String apiKey;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoApprove;

    private int operatorId;

    private Integer vendorId;

    private List<EndpointPermissionModel> endpointPermissionModels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

    public List<EndpointPermissionModel> getEndpointPermissionModels() {
        return endpointPermissionModels;
    }

    public void setEndpointPermissionModels(List<EndpointPermissionModel> endpointPermissionModels) {
        this.endpointPermissionModels = endpointPermissionModels;
    }

    public static ApiAuth toApiAuth(ApiAuthModel apiAuthModel) {
        ApiAuth result = new ApiAuth();
        result.setId(apiAuthModel.getId());
        result.setApiKey(apiAuthModel.getApiKey());
        result.setResourceIds(apiAuthModel.getResourceIds());
        result.setClientSecret(apiAuthModel.getClientSecret());
        result.setScope(apiAuthModel.getScope());
        result.setAuthorizedGrantTypes(apiAuthModel.getAuthorizedGrantTypes());
        result.setWebServerRedirectUri(apiAuthModel.getWebServerRedirectUri());
        result.setAuthorities(apiAuthModel.getAuthorities());
        result.setAccessTokenValidity(apiAuthModel.getAccessTokenValidity());
        result.setRefreshTokenValidity(apiAuthModel.getRefreshTokenValidity());
        result.setAdditionalInformation(apiAuthModel.getAdditionalInformation());
        result.setAutoApprove(apiAuthModel.getAutoApprove());
        result.setOperatorId(apiAuthModel.getOperatorId());
        result.setVendorId(apiAuthModel.getVendorId());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiAuthModel apiAuthModel = (ApiAuthModel) o;
        return id == apiAuthModel.id &&
                Objects.equals(apiKey, apiAuthModel.apiKey) &&
                Objects.equals(clientSecret, apiAuthModel.clientSecret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiKey, clientSecret);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiAuthModel{");
        sb.append("id=").append(id);
        sb.append(", apiKey='").append(apiKey).append('\'');
        sb.append(", resourceIds='").append(resourceIds).append('\'');
        sb.append(", clientSecret='").append(clientSecret).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", authorizedGrantTypes='").append(authorizedGrantTypes).append('\'');
        sb.append(", webServerRedirectUri='").append(webServerRedirectUri).append('\'');
        sb.append(", authorities='").append(authorities).append('\'');
        sb.append(", accessTokenValidity='").append(accessTokenValidity).append('\'');
        sb.append(", refreshTokenValidity='").append(refreshTokenValidity).append('\'');
        sb.append(", additionalInformation='").append(additionalInformation).append('\'');
        sb.append(", autoApprove='").append(autoApprove).append('\'');
        sb.append(", operatorId=").append(operatorId);
        sb.append(", vendorId=").append(vendorId);
        sb.append(", endpointPermissionModels=").append(endpointPermissionModels);
        sb.append('}');
        return sb.toString();
    }
}
