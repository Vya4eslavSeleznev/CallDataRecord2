package com.nexign.cdr.controller;

import com.nexign.cdr.model.CallRecordModel;
import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.cdr.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cdr")
public class DataController {

    private DataService dataService;
    private CallDataRecordEventSender cdrSender;

    public DataController(DataService dataService, CallDataRecordEventSender cdrSender) {
        this.dataService = dataService;
        this.cdrSender = cdrSender;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<List<CallRecordModel>> uploadFile(@RequestParam("file") MultipartFile file) {

       List<CallRecordModel> listOfEvents =  dataService.uploadFile(file);

       if(listOfEvents == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

        return new ResponseEntity<>(listOfEvents, HttpStatus.OK);
    }

    @PostMapping("/sendCdr")
    public ResponseEntity sendEvent() {
        CallRecordModel cdr = new CallRecordModel("02", "89115554433", new Date(), new Date());

        cdrSender.sendEvent(cdr);

        return new ResponseEntity(HttpStatus.OK);
    }
}
