package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.MonkeyApiError;

public interface MonkeyApiErrorService {
    MonkeyApiError updateAndFlush(int id, MonkeyApiError monkeyApiError);
}
