package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.domains.VendorInfo;
import com.monkey.apiManagement.repositories.VendorInfoRepository;
import com.monkey.apiManagement.services.VendorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class VendorInfoController {
    
    @Autowired
    private VendorInfoRepository vendorInfoRepository;
    
    @Autowired
    private VendorInfoService vendorInfoService;
    
    @GetMapping(path = "/vendors")
    public ResponseEntity<List<VendorInfo>> getAll(HttpServletRequest request) {
        List<VendorInfo> vendors = vendorInfoRepository.findAll();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping(path = "/vendors/{id}")
    public ResponseEntity<VendorInfo> getOne(@PathVariable("id") int id,
                                             HttpServletRequest request) {
        VendorInfo vendor = vendorInfoRepository.getOne(id);
        return ResponseEntity.ok(vendor);
    }

    @PostMapping(path = "/vendors")
    public ResponseEntity<VendorInfo> post(@RequestBody VendorInfo vendorInfo,
                                           HttpServletRequest request) {
        VendorInfo vendor = vendorInfoService.saveAndFlush(vendorInfo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(vendor.getId()).toUri();
        return ResponseEntity.created(location).body(vendor);
    }

    @PutMapping(path="/vendors/{id}")
    public ResponseEntity<VendorInfo> put(@PathVariable("id") int id,
                                          @RequestBody VendorInfo vendorInfo,
                                          HttpServletRequest request) {
        VendorInfo updatedVendorInfo = vendorInfoService.updateAndFlush(id, vendorInfo);
        return ResponseEntity.ok(updatedVendorInfo);
    }

    @DeleteMapping(path="/vendors/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        vendorInfoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
