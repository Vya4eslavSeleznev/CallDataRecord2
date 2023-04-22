package com.nexign.brt.controller;

import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.model.CallCostCalculatedEvent;
import com.nexign.brt.service.AccountCallService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
@AllArgsConstructor
public class AccountCallController {

    private AccountCallService accountCallService;

    @PostMapping
    public ResponseEntity<?> addCall(@RequestBody CallCostCalculatedEvent costCalculatedEvent) {
        try {
            accountCallService.addCall(costCalculatedEvent);
        }
        catch(BalanceLessThanZeroException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
