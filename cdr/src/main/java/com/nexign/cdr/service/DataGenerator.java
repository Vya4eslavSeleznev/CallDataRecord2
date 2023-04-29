package com.nexign.cdr.service;

import com.nexign.cdr.exception.EmptyFileException;
import com.nexign.cdr.exception.InvalidInputDataException;

public interface DataGenerator {

    void generate() throws EmptyFileException, InvalidInputDataException;
}
