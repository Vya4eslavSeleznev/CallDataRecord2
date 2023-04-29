package com.nexign.cdr.service;

import com.nexign.cdr.service.impl.DataServiceImpl;
import com.nexign.common.model.CallRecordModel;
import com.nexign.common.model.CallType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {

    @InjectMocks
    private DataServiceImpl dataService;

    @Mock
    private FileContentProvider fileContentProvider;

    private String[] contentArr;
    private List<CallRecordModel> expectedListOfEvents;

    @BeforeEach
    public void init() {
        dataService = new DataServiceImpl(fileContentProvider);

        contentArr = new String[] {
          "INPUT,73734435243,20230725141448,20230725142110"
        };

        Date startDate = null;
        Date endDate = null;

        try {
            startDate = new SimpleDateFormat("yyyyMMddHHmmss").parse("20230725141448".trim());
            endDate = new SimpleDateFormat("yyyyMMddHHmmss").parse("20230725142110".trim());
        }
        catch(ParseException e) {
            e.printStackTrace();
        }

        String phone = "73734435243";
        CallType callType = CallType.INPUT;

        expectedListOfEvents = List.of(
          new CallRecordModel(callType, phone, startDate, endDate)
        );
    }

    @Test
    public void should_upload_file_init_returned_list_of_call_record_models() throws ParseException {
        String content = "content";

        when(fileContentProvider.contentGeneratorForInit(content)).thenReturn(contentArr);

        List<CallRecordModel> actualListOfEvents = dataService.uploadFileInit(content);

        verify(fileContentProvider, times(1)).contentGeneratorForInit(content);

        assertEquals(expectedListOfEvents.get(0).getPhoneNumber(), actualListOfEvents.get(0).getPhoneNumber());
        assertEquals(expectedListOfEvents.get(0).getCallType(), actualListOfEvents.get(0).getCallType());
        assertEquals(expectedListOfEvents.get(0).getEndDate(), actualListOfEvents.get(0).getEndDate());
        assertEquals(expectedListOfEvents.get(0).getStartDate(), actualListOfEvents.get(0).getStartDate());
    }

    @Test
    public void should_upload_file_via_parameters_returned_list_of_call_record_models()
      throws IOException, ParseException {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(fileContentProvider.contentGeneratorForParameters(file)).thenReturn(contentArr);

        List<CallRecordModel> actualListOfEvents = dataService.uploadFileViaParameters(file);

        verify(fileContentProvider, times(1)).contentGeneratorForParameters(file);

        assertEquals(expectedListOfEvents.get(0).getPhoneNumber(), actualListOfEvents.get(0).getPhoneNumber());
        assertEquals(expectedListOfEvents.get(0).getCallType(), actualListOfEvents.get(0).getCallType());
        assertEquals(expectedListOfEvents.get(0).getEndDate(), actualListOfEvents.get(0).getEndDate());
        assertEquals(expectedListOfEvents.get(0).getStartDate(), actualListOfEvents.get(0).getStartDate());
    }
}
