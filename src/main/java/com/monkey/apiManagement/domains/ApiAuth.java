package com.monkey.apiManagement.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monkey.apiManagement.models.ApiAuthModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "dbo", name = "api_auth")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ApiAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "api_key")
    @NotNull
    private String apiKey;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "scope")
    private String scope;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "autoapprove")
    private String autoApprove;

    @Column(name = "operator_id")
    private int operatorId;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, orphanRemoval=true, mappedBy = "apiAuthId")
    private List<EndpointPermission> endpointPermissions;

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

    public static ApiAuthModel toApiAuthModel(ApiAuth apiAuth) {
        ApiAuthModel result = new ApiAuthModel();
        result.setId(apiAuth.getId());
        result.setApiKey(apiAuth.getApiKey());
        result.setResourceIds(apiAuth.getResourceIds());
        result.setClientSecret(apiAuth.getClientSecret());
        result.setScope(apiAuth.getScope());
        result.setAuthorizedGrantTypes(apiAuth.getAuthorizedGrantTypes());
        result.setWebServerRedirectUri(apiAuth.getWebServerRedirectUri());
        result.setAuthorities(apiAuth.getAuthorities());
        result.setAccessTokenValidity(apiAuth.getAccessTokenValidity());
        result.setRefreshTokenValidity(apiAuth.getRefreshTokenValidity());
        result.setAdditionalInformation(apiAuth.getAdditionalInformation());
        result.setAutoApprove(apiAuth.getAutoApprove());
        result.setOperatorId(apiAuth.getOperatorId());
        result.setVendorId(apiAuth.getVendorId());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiAuth apiAuth = (ApiAuth) o;
        return id == apiAuth.id &&
                Objects.equals(apiKey, apiAuth.apiKey) &&
                Objects.equals(clientSecret, apiAuth.clientSecret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiKey, clientSecret);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiAuth{");
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
        sb.append(", endpointPermissions=").append(endpointPermissions);
        sb.append('}');
        return sb.toString();
    }
}
