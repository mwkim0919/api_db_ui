package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.OperatorInfo;

public interface OperatorInfoService {
    OperatorInfo saveAndFlush(OperatorInfo operatorInfo);
    OperatorInfo updateAndFlush(int id, OperatorInfo operatorInfo);
}
