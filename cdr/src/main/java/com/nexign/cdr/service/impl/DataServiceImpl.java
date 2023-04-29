package com.nexign.cdr.service.impl;

import com.nexign.cdr.service.DataService;
import com.nexign.cdr.service.FileContentProvider;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.CallType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    private FileContentProvider fileContentProvider;

    @Override
    public List<CallRecordModel> uploadFileInit(String content) throws ParseException {
        String[] contentArr = fileContentProvider.contentGeneratorForInit(content);
        return parseLogic(contentArr);
    }

    @Override
    public List<CallRecordModel> uploadFileViaParameters(MultipartFile file) throws IOException, ParseException {
        String[] content = fileContentProvider.contentGeneratorForParameters(file);
        return parseLogic(content);
    }

    private List<CallRecordModel> parseLogic(String[] content) throws ParseException {
        List<CallRecordModel> listOfEvents = new ArrayList<>();

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
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.parse(stringDate.trim());
    }
}
