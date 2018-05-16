package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.VendorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorInfoRepository extends JpaRepository<VendorInfo, Integer>,
        JpaSpecificationExecutor<VendorInfo> {
}
