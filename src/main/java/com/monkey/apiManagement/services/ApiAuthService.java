package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.ApiAuth;

public interface ApiAuthService {
    ApiAuth saveAndFlush(ApiAuth apiAuth);

    ApiAuth updateAndFlush(int id, ApiAuth apiauth);
}
