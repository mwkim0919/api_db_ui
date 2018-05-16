package com.monkey.apiManagement.controllers.apiControllers;

import com.monkey.apiManagement.domains.OperatorInfo;
import com.monkey.apiManagement.repositories.OperatorInfoRepository;
import com.monkey.apiManagement.services.OperatorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class OperatorInfoController {

    @Autowired
    private OperatorInfoRepository operatorInfoRepository;

    @Autowired
    private OperatorInfoService operatorInfoService;

    @GetMapping(path = "/operators")
    public ResponseEntity<List<OperatorInfo>> getAll(@RequestParam(required = false, name = "orderBy") String property,
                                                     HttpServletRequest request) {
        List<OperatorInfo> operators = null;
        if ("dsnname".equals(property)) {
            operators = operatorInfoRepository.findAllByOrderByDsnName();
        } else {
            operators = operatorInfoRepository.findAll();
        }
        return ResponseEntity.ok(operators);
    }

    @GetMapping(path = "/operators/{id}")
    public ResponseEntity<OperatorInfo> getOne(@PathVariable("id") int id,
                                               HttpServletRequest request) {
        OperatorInfo operator = operatorInfoRepository.getOne(id);
        return ResponseEntity.ok(operator);
    }

    @PostMapping(path = "/operators")
    public ResponseEntity<OperatorInfo> post(@RequestBody OperatorInfo operatorInfo,
                                             HttpServletRequest request) {
        OperatorInfo operator = operatorInfoService.saveAndFlush(operatorInfo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(operator.getId()).toUri();
        return ResponseEntity.created(location).body(operator);
    }

    @PutMapping(path="/operators/{id}")
    public ResponseEntity<OperatorInfo> put(@PathVariable("id") int id,
                                            @RequestBody OperatorInfo operatorInfo,
                                            HttpServletRequest request) {
        OperatorInfo updatedOperatorInfo = operatorInfoService.updateAndFlush(id, operatorInfo);
        return ResponseEntity.ok(updatedOperatorInfo);
    }

    @DeleteMapping(path="/operators/{id}")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 HttpServletRequest request) {
        operatorInfoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
