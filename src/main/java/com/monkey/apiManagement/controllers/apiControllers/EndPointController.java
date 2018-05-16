package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.domains.EndPoint;
import com.monkey.apiManagement.repositories.EndPointRepository;
import com.monkey.apiManagement.services.EndPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EndPointController {

    @Autowired
    private EndPointRepository endPointRepository;

    @Autowired
    private EndPointService endPointService;

    @GetMapping(path="/endpoints")
    public ResponseEntity<List<EndPoint>> getAll(HttpServletRequest request) {
        List<EndPoint> endPoints = endPointRepository.findAllByOrderByName();
        return ResponseEntity.ok(endPoints);
    }

    @GetMapping(path="/endpoints/{id}")
    public ResponseEntity<EndPoint> getOne(@PathVariable("id") int id,
                                           HttpServletRequest request) {
        EndPoint endPoint = endPointRepository.getOne(id);
        return ResponseEntity.ok(endPoint);
    }

    @PostMapping(path="/endpoints")
    public ResponseEntity<EndPoint> post(@RequestBody EndPoint endPoint,
                                         HttpServletRequest request) {
        EndPoint savedEndPoint = endPointRepository.saveAndFlush(endPoint);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").
                        buildAndExpand(savedEndPoint.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEndPoint);
    }

    @PutMapping(path="/endpoints/{id}")
    public ResponseEntity<EndPoint> put(@PathVariable("id") int id,
                                        @RequestBody EndPoint endPoint,
                                        HttpServletRequest request) {
        EndPoint updatedEndPoint = endPointService.updateAndFlush(id, endPoint);
        return ResponseEntity.ok(updatedEndPoint);
    }

    @DeleteMapping(path="/endpoints/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        endPointRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path="/auths/{id}/missing_endpoints")
    public ResponseEntity<List<EndPoint>> getAllMissingEndpoints(@PathVariable("id") int id,
                                                 HttpServletRequest request) {
        List<EndPoint> missingEndpoints = endPointRepository.getAddabledEndPointsByApiAuthId(id);
        return ResponseEntity.ok(missingEndpoints);
    }
}
