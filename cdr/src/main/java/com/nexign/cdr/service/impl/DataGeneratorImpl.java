package com.nexign.cdr.service.impl;

import com.nexign.cdr.exception.EmptyFileException;
import com.nexign.cdr.exception.InvalidInputDataException;
import com.nexign.cdr.service.CallDataRecordEventSender;
import com.nexign.cdr.service.DataGenerator;
import com.nexign.cdr.service.DataService;
import com.nexign.common.model.CallRecordModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        List<CallRecordModel> listOfEvents = null;
        try {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
              File.separator + "data" + File.separator + "cdr2.txt"
            );

            if(inputStream != null) {
                String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                listOfEvents = dataService.uploadFileInit(text);
                cdrSender.sendEvents(listOfEvents);
            }


//            File resource = new ClassPathResource("data" + File.separator + "cdr2.txt").getFile();
//            System.out.println(resource.toPath());
//            System.out.println(new String(Files.readAllBytes(resource.toPath())));
//            listOfEvents = dataService.uploadFileInit(new String(Files.readAllBytes(resource.toPath())));
        }
        catch(IOException | ParseException e) {
            e.printStackTrace();
            throw new InvalidInputDataException();
        }

//        if(listOfEvents == null) {
//            throw new EmptyFileException();
//        }

        //cdrSender.sendEvents(listOfEvents);
    }
}
