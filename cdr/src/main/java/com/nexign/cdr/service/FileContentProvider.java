package com.nexign.cdr.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileContentProvider {

    String[] contentGeneratorForInit(String file);
    String[] contentGeneratorForParameters(MultipartFile file) throws IOException;
}
