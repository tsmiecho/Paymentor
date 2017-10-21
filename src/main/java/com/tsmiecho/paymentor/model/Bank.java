package com.tsmiecho.paymentor.model;

import lombok.Getter;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Getter
public class Bank {

    static final String EMPTY_MESSAGE = "Name of a bank cannot be empty";

    private long id;

    private String name;

    public Bank(long id, String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        this.id = id;
        this.name = name;
    }
}
