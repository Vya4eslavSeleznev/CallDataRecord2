package com.nexign.cdr.service.impl;

import com.nexign.cdr.service.DataService;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.CallType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public List<CallRecordModel> uploadFile(MultipartFile file) throws IOException, ParseException {
        List<CallRecordModel> listOfEvents = new ArrayList<>();
        String[] content = new String[0];

        content = new String(file.getBytes()).split("\r\n");

        for( String str : content) {
            String[] callRecord = str.split(",");

            if(callRecord.length != 4) {
                return null;
            }

            listOfEvents.add(
              new CallRecordModel(
                CallType.valueOf(callRecord[0]),
                callRecord[1],
                stringToDate(callRecord[2]),
                stringToDate(callRecord[3]))
            );
        }

        return listOfEvents;
    }

    private Date stringToDate(String stringDate) throws ParseException {
        Date date;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        date = df.parse(stringDate.trim());
        return date;
    }
}
