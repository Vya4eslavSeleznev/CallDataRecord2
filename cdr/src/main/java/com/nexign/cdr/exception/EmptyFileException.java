package com.nexign.cdr.exception;

public class EmptyFileException extends Exception {

    public EmptyFileException() {
        super("File is empty");
    }
}
