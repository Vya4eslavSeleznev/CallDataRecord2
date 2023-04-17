package com.nexign.cdr.service;

import com.nexign.cdr.model.CallRecordModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataService {

    List<CallRecordModel> uploadFile(MultipartFile file);
}
