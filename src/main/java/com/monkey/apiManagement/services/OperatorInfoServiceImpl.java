package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.OperatorInfo;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.OperatorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OperatorInfoServiceImpl implements OperatorInfoService {

    @Autowired
    private OperatorInfoRepository operatorInfoRepository;

    @Override
    public OperatorInfo saveAndFlush(OperatorInfo operatorInfo) {
        operatorInfo.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        OperatorInfo result = operatorInfoRepository.saveAndFlush(operatorInfo);
        return result;
    }

    @Override
    public OperatorInfo updateAndFlush(int id, OperatorInfo operatorInfo) {
        OperatorInfo result = null;
        OperatorInfo retrievedOperatorInfo = operatorInfoRepository.getOne(id);
        if (retrievedOperatorInfo == null) {
            throw new NotFoundException(
                    new StringBuilder("Operator with id: ").append(id).append(", not found.").toString()
            );
        } else {
            if (operatorInfo.getId() != null) {
                throw new ReadOnlyException("Id is read-only.");
            }
            if (operatorInfo.getName() != null) {
                retrievedOperatorInfo.setName(operatorInfo.getName());
            }
            if (operatorInfo.getCfUrl() != null) {
                retrievedOperatorInfo.setCfUrl(operatorInfo.getCfUrl());
            }
            if (operatorInfo.getDsnName() != null) {
                retrievedOperatorInfo.setDsnName(operatorInfo.getDsnName());
            }
            if (operatorInfo.getIdentifier() != null) {
                retrievedOperatorInfo.setIdentifier(operatorInfo.getIdentifier());
            }
            retrievedOperatorInfo.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        result = operatorInfoRepository.saveAndFlush(retrievedOperatorInfo);
        return result;
    }
}
