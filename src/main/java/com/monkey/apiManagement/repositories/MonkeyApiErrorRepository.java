package com.monkey.apiManagement.repositories;

import com.monkey.apiManagement.domains.MonkeyApiError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MonkeyApiErrorRepository extends JpaRepository<MonkeyApiError, Integer>,
        JpaSpecificationExecutor<MonkeyApiError> {
}
