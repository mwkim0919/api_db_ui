package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.domains.ApiAuth;
import com.monkey.apiManagement.repositories.ApiAuthRepository;
import com.monkey.apiManagement.services.ApiAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiAuthController {

    @Autowired
    private ApiAuthRepository apiAuthRepository;

    @Autowired
    private ApiAuthService apiAuthService;

    @GetMapping(path="/auths")
    public ResponseEntity<List<ApiAuth>> getAll(HttpServletRequest request) {
        List<ApiAuth> apiAuths = apiAuthRepository.findAll();
        return ResponseEntity.ok(apiAuths);
    }

    @GetMapping(path="/auths/{id}")
    public ResponseEntity<ApiAuth> getOne(@PathVariable("id") int id,
                                          HttpServletRequest request) {
        ApiAuth apiAuth = apiAuthRepository.getOne(id);
        return ResponseEntity.ok(apiAuth);
    }

    @PostMapping(path="/auths")
    public ResponseEntity<ApiAuth> post(@RequestBody ApiAuth apiAuth,
                                        HttpServletRequest request) {
        ApiAuth savedApiAuth = apiAuthService.saveAndFlush(apiAuth);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").
                        buildAndExpand(savedApiAuth.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedApiAuth);
    }

    @PutMapping(path="/auths/{id}")
    public ResponseEntity<ApiAuth> put(@PathVariable("id") int id,
                                       @RequestBody ApiAuth apiAuth,
                                       HttpServletRequest request) {
        ApiAuth updatedApiAuth = apiAuthService.updateAndFlush(id, apiAuth);
        return ResponseEntity.ok(updatedApiAuth);
    }

    @DeleteMapping(path="/auths/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        apiAuthRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
