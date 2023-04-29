package com.nexign.cdr.service.impl;

import com.nexign.cdr.service.FileContentProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileContentProviderImpl implements FileContentProvider {

    @Override
    public String[] contentGeneratorForInit(String content) {
        return content.split("\r\n");
    }

    @Override
    public String[] contentGeneratorForParameters(MultipartFile file) throws IOException {
        return new String(file.getBytes()).split("\r\n");
    }
}
