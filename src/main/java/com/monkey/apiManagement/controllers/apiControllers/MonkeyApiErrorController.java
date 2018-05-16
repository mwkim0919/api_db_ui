package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.domains.MonkeyApiError;
import com.monkey.apiManagement.repositories.MonkeyApiErrorRepository;
import com.monkey.apiManagement.services.MonkeyApiErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/apierrors")
public class MonkeyApiErrorController {

    @Autowired
    private MonkeyApiErrorRepository monkeyApiErrorRepository;

    @Autowired
    private MonkeyApiErrorService monkeyApiErrorService;

    @GetMapping()
    public ResponseEntity<List<MonkeyApiError>> getAll(HttpServletRequest request) {
        List<MonkeyApiError> apiErrors = monkeyApiErrorRepository.findAll();
        return ResponseEntity.ok(apiErrors);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<MonkeyApiError> getOne(@PathVariable("id") int id,
                                                 HttpServletRequest request) {
        MonkeyApiError apiError = monkeyApiErrorRepository.getOne(id);
        return ResponseEntity.ok(apiError);
    }

    @PostMapping()
    public ResponseEntity<MonkeyApiError> post(@RequestBody MonkeyApiError monkeyApiError,
                                               HttpServletRequest request) {
        MonkeyApiError apiError = monkeyApiErrorRepository.saveAndFlush(monkeyApiError);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(apiError.getErrorCode()).toUri();
        return ResponseEntity.created(location).body(apiError);
    }

    @PutMapping(path="/{id}")
    public MonkeyApiError put(@PathVariable("id") int id,
                              @RequestBody MonkeyApiError monkeyApiError,
                              HttpServletRequest request) {
        MonkeyApiError apiError = monkeyApiErrorService.updateAndFlush(id, monkeyApiError);
        return apiError;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        monkeyApiErrorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
