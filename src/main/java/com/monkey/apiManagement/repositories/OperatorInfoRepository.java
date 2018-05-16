package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.OperatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorInfoRepository extends JpaRepository<OperatorInfo, Integer>,
        JpaSpecificationExecutor<OperatorInfo> {

    List<OperatorInfo> findAllByOrderByDsnName();
}
