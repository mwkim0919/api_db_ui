package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.daos.EndpointPermissionDao;
import com.monkey.apiManagement.domains.EndpointPermission;
import com.monkey.apiManagement.repositories.EndpointPermissionRepository;
import com.monkey.apiManagement.services.EndpointPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EndpointPermissionController {

    @Autowired
    private EndpointPermissionRepository endpointPermissionRepository;

    @Autowired
    private EndpointPermissionService endpointPermissionService;

    @Autowired
    private EndpointPermissionDao endpointPermissionDao;

    @GetMapping(path="/permissions")
    public ResponseEntity<List<EndpointPermission>> getAll(HttpServletRequest request) {
        List<EndpointPermission> endpointPermissions = endpointPermissionRepository.findAll();
        return ResponseEntity.ok(endpointPermissions);
    }

    @GetMapping(path="/permissions/{id}")
    public ResponseEntity<EndpointPermission> getOne(@PathVariable("id") int id,
                                                     HttpServletRequest request) {
        EndpointPermission endpointPermission = endpointPermissionRepository.getOne(id);
        return ResponseEntity.ok(endpointPermission);
    }

    @PostMapping(path="/permissions")
    public ResponseEntity<EndpointPermission> post(@RequestBody EndpointPermission EndpointPermission,
                                                   HttpServletRequest request) {
        EndpointPermission endpointPermission = endpointPermissionService.saveAndFlush(EndpointPermission);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(endpointPermission.getId()).toUri();
        return ResponseEntity.created(location).body(endpointPermission);
    }

    @PutMapping(path="/permissions/{id}")
    public ResponseEntity<EndpointPermission> put(@PathVariable("id") int id,
                                                  @RequestBody EndpointPermission EndpointPermission,
                                                  HttpServletRequest request) {
        EndpointPermission endpointPermission = endpointPermissionService.updateAndFlush(id, EndpointPermission);
        return ResponseEntity.ok(endpointPermission);
    }

    @DeleteMapping(path="/permissions/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        endpointPermissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path="/auths/{id}/permissions")
    public List<EndpointPermission> getPermissionsForApiAuthId(@PathVariable("id") int id,
                                                               HttpServletRequest request) {
        List<EndpointPermission> endpointPermissions = endpointPermissionRepository.getEndpointPermissionsByApiAuthIdV2(id);
        return endpointPermissions;
    }

    @PostMapping(path="/auths/{id}/permissions")
    public ResponseEntity postAllPermissions(@PathVariable("id") int id) {
        boolean result = endpointPermissionDao.insertPermissionsForAuth(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping(path="/auths/{id}/permissions")
    public ResponseEntity deleteAllPermissions(@PathVariable("id") int id) {
        boolean result = endpointPermissionDao.deleteAllPermissionsForAuth(id);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
