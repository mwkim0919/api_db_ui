package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.MonkeyApiError;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.MonkeyApiErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonkeyApiErrorServiceImpl implements MonkeyApiErrorService {

    @Autowired
    private MonkeyApiErrorRepository monkeyApiErrorRepository;

    @Override
    public MonkeyApiError updateAndFlush(int id, MonkeyApiError monkeyApiError) {
        MonkeyApiError result = null;
        MonkeyApiError retrievedMonkeyApiError = monkeyApiErrorRepository.getOne(id);
        if (retrievedMonkeyApiError == null) {
            throw new NotFoundException(
                    new StringBuilder("Monkey api error with id: ").append(id).append(", not found.").toString()
            );
        } else {
            if (monkeyApiError.getErrorCode() != null) {
                throw new ReadOnlyException("Id is read-only.");
            }
            if (monkeyApiError.getErrorMessage() != null) {
                retrievedMonkeyApiError.setErrorMessage(monkeyApiError.getErrorMessage());
            }
            if (monkeyApiError.getDeveloperMessage() != null) {
                retrievedMonkeyApiError.setDeveloperMessage(monkeyApiError.getDeveloperMessage());
            }
            if (monkeyApiError.getAdditionalInfo() != null) {
                retrievedMonkeyApiError.setAdditionalInfo(monkeyApiError.getAdditionalInfo());
            }
        }
        result = monkeyApiErrorRepository.saveAndFlush(retrievedMonkeyApiError);
        return result;
    }
}
