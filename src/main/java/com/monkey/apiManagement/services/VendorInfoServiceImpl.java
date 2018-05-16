package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.VendorInfo;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.VendorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class VendorInfoServiceImpl implements VendorInfoService {

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Override
    public VendorInfo saveAndFlush(VendorInfo vendorInfo) {
        vendorInfo.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        VendorInfo result = vendorInfoRepository.saveAndFlush(vendorInfo);
        return result;
    }
    @Override
    public VendorInfo updateAndFlush(int id, VendorInfo vendorInfo) {
        VendorInfo result = null;
        VendorInfo retrievedVendorInfo = vendorInfoRepository.getOne(id);
        if (retrievedVendorInfo == null) {
            throw new NotFoundException(
                    new StringBuilder("Vendor with id: ").append(id).append(", not found.").toString()
            );
        } else {
            if (vendorInfo.getId() != null) {
                throw new ReadOnlyException("Id is read-only.");
            }
            if (vendorInfo.getName() != null) {
                retrievedVendorInfo.setName(vendorInfo.getName());
            }
            retrievedVendorInfo.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        result = vendorInfoRepository.saveAndFlush(retrievedVendorInfo);
        return result;
    }
}
