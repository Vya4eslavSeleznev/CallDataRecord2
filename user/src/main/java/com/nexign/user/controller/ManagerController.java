package com.nexign.user.controller;

import com.nexign.common.model.UserCredentialModel;
import com.nexign.user.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {

    private ManagerService managerService;

    @PostMapping("/profile")
    public ResponseEntity<?> createManager(@RequestBody UserCredentialModel userCredentialModel) {
        managerService.saveManager(userCredentialModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
