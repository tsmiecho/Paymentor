package com.tsmiecho.paymentor.service;

import com.tsmiecho.paymentor.exception.NoSuchBankException;
import com.tsmiecho.paymentor.exception.TechnicalException;

/**
 * Created by Tomasz Śmiechowicz on 23.10.17.
 */
public interface BankLifecycleService {
    boolean isBankSuspended(String bankId) throws NoSuchBankException, TechnicalException;
}
