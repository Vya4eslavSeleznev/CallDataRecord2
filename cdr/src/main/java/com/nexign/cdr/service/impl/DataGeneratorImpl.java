package com.nexign.cdr.service.impl;

import com.nexign.cdr.exception.EmptyFileException;
import com.nexign.cdr.exception.InvalidInputDataException;
import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.cdr.service.DataGenerator;
import com.nexign.cdr.service.DataService;
import com.nexign.common.model.CallRecordModel;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class DataGeneratorImpl implements DataGenerator {

    private DataService dataService;
    private CallDataRecordEventSender cdrSender;

    @PostConstruct
    @Override
    public void generate() throws EmptyFileException, InvalidInputDataException {
        List<CallRecordModel> listOfEvents;
        try {
            File resource = new ClassPathResource("data/cdr2.txt").getFile();
            listOfEvents = dataService.uploadFileInit(new String(Files.readAllBytes(resource.toPath())));
        }
        catch(IOException | ParseException e) {
            throw new InvalidInputDataException();
        }

        if(listOfEvents == null) {
            throw new EmptyFileException();
        }

        cdrSender.sendEvents(listOfEvents);
    }
}
