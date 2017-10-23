package com.tsmiecho.paymentor.service;

import com.tsmiecho.paymentor.exception.NoSuchBankException;
import com.tsmiecho.paymentor.exception.TechnicalException;
import org.springframework.stereotype.Service;

/**
 * Created by Tomasz Śmiechowicz on 23.10.17.
 */
@Service
public class BankLifecycleServiceMocked implements BankLifecycleService {

    @Override
    public boolean isBankSuspended(String bankId) throws NoSuchBankException, TechnicalException {
        return false;
    }
}
