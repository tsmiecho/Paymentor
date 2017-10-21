package com.tsmiecho.paymentor.model;

import lombok.Getter;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Getter
public class PrimaryAccountNumber {

    static final String EMPTY_MESSAGE = "Primary account number cannot be empty";

    static final String ILLEGAL_CHARACTER_MESSAGE = "Primary account number can contain only digits";

    static final String ILLEGAL_ISSUER_IDENTIFICATION_SIZE_MESSAGE = "Size of the issuer identification number is incorrect";

    static final int MINIMAL_ISSUER_IDENTIFICATION_NUMBER_SIZE = 2;

    IssuerIdentificationNumber issuerIdentificationNumber;

    String individualAccountIdentifier;

    public PrimaryAccountNumber(String number, int issuerIdentificationNumberSize) {
        if(number == null || number.trim().isEmpty()){
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        if(!number.matches("^\\d*$")){
            throw new IllegalArgumentException(ILLEGAL_CHARACTER_MESSAGE);
        }
        if(issuerIdentificationNumberSize > number.length() || issuerIdentificationNumberSize < MINIMAL_ISSUER_IDENTIFICATION_NUMBER_SIZE){
            throw new IllegalArgumentException(ILLEGAL_ISSUER_IDENTIFICATION_SIZE_MESSAGE);
        }
        final String trimmedNumber = number.trim();
        this.issuerIdentificationNumber = new IssuerIdentificationNumber(trimmedNumber.substring(0, issuerIdentificationNumberSize));
        this.individualAccountIdentifier = trimmedNumber.substring(issuerIdentificationNumberSize, trimmedNumber.length());
    }
}
