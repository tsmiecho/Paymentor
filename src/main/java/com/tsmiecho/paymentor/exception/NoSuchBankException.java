package com.tsmiecho.paymentor.exception;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class NoSuchBankException extends Exception{

    public NoSuchBankException(String message) {
        super(message);
    }
}
