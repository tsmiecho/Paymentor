package com.tsmiecho.paymentor.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Getter
@ToString
public class IssuerIdentificationNumber {

    static final String EMPTY_MESSAGE = "Issuer identification number cannot be empty";

    static final String ILLEGAL_CHARACTER_MESSAGE = "Issuer identification number can contain only digits";

    private String number;

    public IssuerIdentificationNumber(String number) {
        if(number == null || number.trim().isEmpty()){
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        if(!number.matches("^\\d*$")){
            throw new IllegalArgumentException(ILLEGAL_CHARACTER_MESSAGE);
        }
        this.number = number;
    }
}
