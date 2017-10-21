package com.tsmiecho.paymentor;

import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import com.tsmiecho.paymentor.model.PrimaryAccountNumber;
import com.tsmiecho.paymentor.repository.IssuerIdentificationNumberRangeRepository;
import com.tsmiecho.paymentor.repository.NoSuchBankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Service
public class IssuerIdentificationNumberRangeService {

    private final IssuerIdentificationNumberRangeRepository repository;

    @Autowired
    public IssuerIdentificationNumberRangeService(IssuerIdentificationNumberRangeRepository repository) {
        this.repository = repository;
    }

    public Bank determineBank(PrimaryAccountNumber pan) throws NoSuchBankException {
        return determineBank(pan.getIssuerIdentificationNumber());
    }

    public Bank determineBank(IssuerIdentificationNumber inn) throws NoSuchBankException {
        return repository.findByIssuerIdentificationNumber(inn);

    }
}