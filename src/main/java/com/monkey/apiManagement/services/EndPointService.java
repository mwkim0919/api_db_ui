package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.EndPoint;

public interface EndPointService {
    EndPoint updateAndFlush(int id, EndPoint endpoint);
}
