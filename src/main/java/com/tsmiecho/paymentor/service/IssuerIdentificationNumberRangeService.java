package com.tsmiecho.paymentor.service;

import com.tsmiecho.paymentor.exception.NoSuchBankException;
import com.tsmiecho.paymentor.exception.TechnicalException;
import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import com.tsmiecho.paymentor.model.PrimaryAccountNumber;
import com.tsmiecho.paymentor.repository.IssuerIdentificationNumberRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Service
public class IssuerIdentificationNumberRangeService {

    private final IssuerIdentificationNumberRangeRepository repository;

    private final BankLifecycleService bankLifecycleService;

    @Autowired
    public IssuerIdentificationNumberRangeService(IssuerIdentificationNumberRangeRepository repository, BankLifecycleService bankLifecycleService) {
        this.repository = repository;
        this.bankLifecycleService = bankLifecycleService;
    }

    public Bank findBank(PrimaryAccountNumber pan) throws NoSuchBankException, TechnicalException {
        return findBank(pan.getIssuerIdentificationNumber());
    }

    public Bank findBank(IssuerIdentificationNumber inn) throws NoSuchBankException, TechnicalException {
        final Bank bank = repository.findByIssuerIdentificationNumber(inn);
        return new Bank(bank.getId(), bank.getName(), bankLifecycleService.isBankSuspended(String.valueOf(bank.getId())));

    }
}
