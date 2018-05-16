package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.VendorInfo;

public interface VendorInfoService {
    VendorInfo saveAndFlush(VendorInfo vendorInfo);
    VendorInfo updateAndFlush(int id, VendorInfo vendorInfo);
}
