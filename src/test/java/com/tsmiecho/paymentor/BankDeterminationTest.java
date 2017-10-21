package com.tsmiecho.paymentor;

import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import com.tsmiecho.paymentor.model.PrimaryAccountNumber;
import com.tsmiecho.paymentor.repository.IssuerIdentificationNumberRangeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class BankDeterminationTest {

    private IssuerIdentificationNumberRangeService service;

    private Bank mockedBank = new Bank(2L, "nBank");

    @Before
    public void setUp() throws Exception {
        final IssuerIdentificationNumberRangeRepository mockedRepository = Mockito.mock(IssuerIdentificationNumberRangeRepository.class);
        Mockito.when(mockedRepository.findByIssuerIdentificationNumber(Mockito.anyObject())).thenReturn(mockedBank);
        service = new IssuerIdentificationNumberRangeService(mockedRepository);
    }

    @Test
    public void bankWillBeFoundByValidPrimaryAccountNumber() throws Exception {
        final PrimaryAccountNumber pan = new PrimaryAccountNumber("5217123456789113", 6);
        final Bank bank = service.determineBank(pan);

        assertThat(bank).isEqualToComparingFieldByField(mockedBank);
    }

    @Test
    public void bankWillBeFoundByValidIssuerIdentificationNumber() throws Exception {
        final IssuerIdentificationNumber iin = new IssuerIdentificationNumber("52");
        final Bank bank = service.determineBank(iin);

        assertThat(bank).isEqualToComparingFieldByField(mockedBank);
    }
}