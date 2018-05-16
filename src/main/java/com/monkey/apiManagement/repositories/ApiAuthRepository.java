package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.ApiAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiAuthRepository extends JpaRepository<ApiAuth, Integer>, JpaSpecificationExecutor<ApiAuth> {
}
