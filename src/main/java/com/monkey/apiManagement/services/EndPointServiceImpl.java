package com.monkey.apiManagement.services;

import com.monkey.apiManagement.domains.EndPoint;
import com.monkey.apiManagement.exceptions.NotFoundException;
import com.monkey.apiManagement.exceptions.ReadOnlyException;
import com.monkey.apiManagement.repositories.EndPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndPointServiceImpl implements EndPointService {

    @Autowired
    private EndPointRepository endPointRepository;

    @Override
    public EndPoint updateAndFlush(int id, EndPoint endpoint) {
        EndPoint result = null;
        EndPoint retrievedEndPoint = endPointRepository.getOne(id);
        if (retrievedEndPoint == null) {
            throw new NotFoundException(
                    new StringBuilder("Endpoint with id: ").append(id).append(", not found").toString()
            );
        } else {
            if (endpoint.getId() != null) {
                throw new ReadOnlyException("Id is read-only");
            }
            if (endpoint.getName() != null) {
                retrievedEndPoint.setName(endpoint.getName());
            }
        }
        result = endPointRepository.saveAndFlush(retrievedEndPoint);
        return result;
    }

}
