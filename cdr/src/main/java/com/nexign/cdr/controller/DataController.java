package com.nexign.cdr.controller;

import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.cdr.service.DataService;
import com.nexign.common.model.CallRecordModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cdr")
@AllArgsConstructor
public class DataController {

    private DataService dataService;
    private CallDataRecordEventSender cdrSender;

    @RequestMapping(
      path = "/file",
      method = RequestMethod.POST,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<List<CallRecordModel>> uploadFile(@RequestPart("file") MultipartFile file) {
        List<CallRecordModel> listOfEvents;
        try {
            listOfEvents = dataService.uploadFileViaParameters(file);
        }
        catch(IOException | ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(listOfEvents == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        cdrSender.sendEvents(listOfEvents);

        return new ResponseEntity<>(listOfEvents, HttpStatus.OK);
    }
}
