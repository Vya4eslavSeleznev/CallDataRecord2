package com.nexign.cdr.controller;

import com.nexign.cdr.model.CallRecordModel;
import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.cdr.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cdr")
@AllArgsConstructor
public class DataController {

    private DataService dataService;
    private CallDataRecordEventSender cdrSender;

    @PostMapping("/file")
    public ResponseEntity<List<CallRecordModel>> uploadFile(@RequestParam("file") MultipartFile file) {
        List<CallRecordModel> listOfEvents =  dataService.uploadFile(file);

       if(listOfEvents == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

       cdrSender.sendEvents(listOfEvents);

       return new ResponseEntity<>(listOfEvents, HttpStatus.OK);
    }
}
