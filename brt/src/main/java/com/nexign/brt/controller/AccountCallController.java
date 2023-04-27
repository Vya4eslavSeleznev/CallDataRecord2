package com.nexign.brt.controller;

import com.nexign.brt.exception.AccountNotFoundException;
import com.nexign.brt.exception.BalanceLessThanZeroException;
import com.nexign.brt.service.AccountCallService;
import com.nexign.common.model.CallCostCalculatedEvent;
import com.nexign.common.model.UserCallsModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/call")
@AllArgsConstructor
public class AccountCallController {

    private AccountCallService accountCallService;

    @PostMapping
    public ResponseEntity<?> addCall(@RequestBody CallCostCalculatedEvent costCalculatedEvent) {
        try {
            accountCallService.addCall(costCalculatedEvent);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(BalanceLessThanZeroException | AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserCallsModel> customerCalls(@PathVariable long userId) {
        return new ResponseEntity<>(accountCallService.findUserCalls(userId), HttpStatus.OK);
    }
}
