package com.nexign.cdr.service.impl;

import com.nexign.cdr.model.CallRecordModel;
import com.nexign.cdr.service.DataService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public List<CallRecordModel> uploadFile(MultipartFile file) {
        List<CallRecordModel> listOfEvents = new ArrayList<>();
        String[] content = new String[0];

        try {
            content = new String(file.getBytes()).split("\r\n");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        for(String str : content) {
            String[] callRecord = str.split(", ");

            if(callRecord.length != 4) {
                return null;
            }

            listOfEvents.add(
              new CallRecordModel(
                callRecord[0],
                callRecord[1],
                stringToDate(callRecord[2]),
                stringToDate(callRecord[3])));
        }

        return listOfEvents;
    }

    @Override
    public void createEvent() {

    }

    private Date stringToDate(String stringDate) {
        Date date;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            date = df.parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: ", e);
        }

        return date;
    }
}
