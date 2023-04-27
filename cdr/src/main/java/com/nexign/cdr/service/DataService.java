package com.nexign.cdr.service;

import com.nexign.common.model.CallRecordModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface DataService {

    List<CallRecordModel> uploadFile(MultipartFile file) throws IOException, ParseException;
}
