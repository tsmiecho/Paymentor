package com.tsmiecho.paymentor.repository;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class NoSuchBankException extends Exception{

    NoSuchBankException(String message) {
        super(message);
    }
}
