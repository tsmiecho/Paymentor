package com.tsmiecho.paymentor.service;

import com.tsmiecho.paymentor.exception.NoSuchBankException;
import com.tsmiecho.paymentor.exception.TechnicalException;
import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import com.tsmiecho.paymentor.model.PrimaryAccountNumber;
import com.tsmiecho.paymentor.repository.IssuerIdentificationNumberRangeRepository;
import lombok.extern.log4j.Log4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Service
@Log4j
public class IssuerIdentificationNumberRangeService {

    private final IssuerIdentificationNumberRangeRepository repository;

    private final BankLifecycleService bankLifecycleService;

    @Autowired
    public IssuerIdentificationNumberRangeService(IssuerIdentificationNumberRangeRepository repository, BankLifecycleService bankLifecycleService) {
        this.repository = repository;
        this.bankLifecycleService = bankLifecycleService;
    }

    public Bank findBank(PrimaryAccountNumber pan) throws NoSuchBankException, TechnicalException{
        if(pan == null){
            throw new IllegalArgumentException("Primary account number cannot be null");
        }
        return findBank(pan.getIssuerIdentificationNumber());
    }

    public Bank findBank(IssuerIdentificationNumber inn) throws NoSuchBankException, TechnicalException {
        if(inn == null){
            throw new IllegalArgumentException("Issuer identification number cannot be null");
        }
        final Bank bank = repository.findByIssuerIdentificationNumber(inn);
        return new Bank(bank.getId(), bank.getName(), bankLifecycleService.isBankSuspended(String.valueOf(bank.getId())));
    }

    public List<Pair<PrimaryAccountNumber, Bank>> findBanksByPrimaryAccountNumbers(List<PrimaryAccountNumber> pans) {
        if(CollectionUtils.isEmpty(pans)){
            throw new IllegalArgumentException("List of primary account numbers cannot be empty");
        }
        return pans.stream()
                .map(getPrimaryAccountNumberPairFunction())
                .collect(Collectors.toList());
    }

    public List<Pair<IssuerIdentificationNumber, Bank>> findBanksByIssuerIdentificationNumbers(List<IssuerIdentificationNumber> inns) {
        if(CollectionUtils.isEmpty(inns)){
            throw new IllegalArgumentException("List of issuer identification numbers cannot be empty");
        }
        return inns.stream()
                .map(getIssuerIdentificationNumberPairFunction())
                .collect(Collectors.toList());
    }

    private Function<PrimaryAccountNumber, Pair<PrimaryAccountNumber, Bank>> getPrimaryAccountNumberPairFunction() {
        return pan -> {
            Bank bankFromRepository;
            try {
                bankFromRepository = repository.findByIssuerIdentificationNumber(pan.getIssuerIdentificationNumber());
                final boolean isBankSuspended = bankLifecycleService.isBankSuspended(String.valueOf(bankFromRepository.getId()));
                return new Pair<>(pan, new Bank(bankFromRepository.getId(), bankFromRepository.getName(), isBankSuspended));
            } catch (NoSuchBankException | TechnicalException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e);
                }
                return new Pair<>(pan, null);
            }
        };
    }

    private Function<IssuerIdentificationNumber, Pair<IssuerIdentificationNumber, Bank>> getIssuerIdentificationNumberPairFunction() {
        return inn -> {
            Bank bankFromRepository;
            try {
                bankFromRepository = repository.findByIssuerIdentificationNumber(inn);
                final boolean isBankSuspended = bankLifecycleService.isBankSuspended(String.valueOf(bankFromRepository.getId()));
                return new Pair<>(inn, new Bank(bankFromRepository.getId(), bankFromRepository.getName(), isBankSuspended));
            } catch (NoSuchBankException | TechnicalException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e);
                }
                return new Pair<>(inn, null);
            }
        };
    }

}
