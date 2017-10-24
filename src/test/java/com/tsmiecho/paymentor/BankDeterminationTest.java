package com.tsmiecho.paymentor;

import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import com.tsmiecho.paymentor.model.PrimaryAccountNumber;
import com.tsmiecho.paymentor.repository.IssuerIdentificationNumberRangeRepository;
import com.tsmiecho.paymentor.service.BankLifecycleService;
import com.tsmiecho.paymentor.service.IssuerIdentificationNumberRangeService;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class BankDeterminationTest {

    private IssuerIdentificationNumberRangeService service;

    private Bank mockedBank = new Bank(2L, "nBank");

    @Before
    public void setUp() throws Exception {
        final BankLifecycleService bankLifecycleService = Mockito.mock(BankLifecycleService.class);
        Mockito.when(bankLifecycleService.isBankSuspended(Mockito.anyString())).thenReturn(false);
        service = new IssuerIdentificationNumberRangeService(new IssuerIdentificationNumberRangeRepository(), bankLifecycleService);
    }

    @Test
    public void bankWillBeFoundByValidPrimaryAccountNumber() throws Exception {
        final PrimaryAccountNumber pan = new PrimaryAccountNumber("5217123456789113", 6);
        final Bank bank = service.findBank(pan);

        assertThat(bank).isEqualToComparingFieldByField(mockedBank);
    }

    @Test
    public void bankWillBeFoundByValidIssuerIdentificationNumber() throws Exception {
        final IssuerIdentificationNumber iin = new IssuerIdentificationNumber("52");
        final Bank bank = service.findBank(iin);

        assertThat(bank).isEqualToComparingFieldByField(mockedBank);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWillBeThrownIfIssuerIdentificationNumberWillBeNull() throws Exception {
        IssuerIdentificationNumber inn = null;
        service.findBank(inn);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWillBeThrownIfPrimaryAccountNumberWillBeNull() throws Exception {
        PrimaryAccountNumber pan = null;
        service.findBank(pan);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWillBeThrownIfPrimaryAccountNumbersWillBeEmpty() throws Exception {
        List<PrimaryAccountNumber> pans = null;
        service.findBanksByPrimaryAccountNumbers(pans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWillBeThrownIfIssuerIdentificationNumbersWillBeEmpty() throws Exception {
        List<IssuerIdentificationNumber> inns = Collections.emptyList();
        service.findBanksByIssuerIdentificationNumbers(inns);
    }

    @Test
    public void primaryAccountNumberPairsWillBeFound() throws Exception {
        final PrimaryAccountNumber pan = new PrimaryAccountNumber("6017123456789113", 6);
        final PrimaryAccountNumber pan2 = new PrimaryAccountNumber("5300123456789113", 4);
        final List<Pair<PrimaryAccountNumber, Bank>> pairs = service.findBanksByPrimaryAccountNumbers(Arrays.asList(pan, pan2));
        Assert.assertTrue(pairs != null);
        Assert.assertTrue(pairs.size() == 2);
        Assert.assertEquals("601712", pairs.get(0).getValue0().getIssuerIdentificationNumber().getNumber());
        Assert.assertEquals("5300", pairs.get(1).getValue0().getIssuerIdentificationNumber().getNumber());
        Assert.assertEquals("Kakao SA", pairs.get(0).getValue1().getName());
        Assert.assertEquals("OMG Bank Łódzki", pairs.get(1).getValue1().getName());
    }

    @Test
    public void primaryAccountNumberPairsWillNotBeFound() throws Exception {
        final PrimaryAccountNumber pan = new PrimaryAccountNumber("1017123456789113", 6);
        final List<Pair<PrimaryAccountNumber, Bank>> pairs = service.findBanksByPrimaryAccountNumbers(Collections.singletonList(pan));
        Assert.assertTrue(pairs != null);
        Assert.assertTrue(pairs.size() == 1);
        Assert.assertEquals("101712", pairs.get(0).getValue0().getIssuerIdentificationNumber().getNumber());
        Assert.assertTrue(pairs.get(0).getValue1() == null);
    }

    @Test
    public void issuerIdentificationNumberPairsWillBeFound() throws Exception {
        final IssuerIdentificationNumber inn = new IssuerIdentificationNumber("601712");
        final IssuerIdentificationNumber inn2 = new IssuerIdentificationNumber("5300");
        final List<Pair<IssuerIdentificationNumber, Bank>> pairs = service.findBanksByIssuerIdentificationNumbers(Arrays.asList(inn, inn2));
        Assert.assertTrue(pairs != null);
        Assert.assertTrue(pairs.size() == 2);
        Assert.assertEquals("601712", pairs.get(0).getValue0().getNumber());
        Assert.assertEquals("5300", pairs.get(1).getValue0().getNumber());
        Assert.assertEquals("Kakao SA", pairs.get(0).getValue1().getName());
        Assert.assertEquals("OMG Bank Łódzki", pairs.get(1).getValue1().getName());
    }

    @Test
    public void issuerIdentificationNumberPairsWillNotBeFound() throws Exception {
        final IssuerIdentificationNumber inn = new IssuerIdentificationNumber("101712");
        final List<Pair<IssuerIdentificationNumber, Bank>> pairs = service.findBanksByIssuerIdentificationNumbers(Collections.singletonList(inn));
        Assert.assertTrue(pairs != null);
        Assert.assertTrue(pairs.size() == 1);
        Assert.assertEquals("101712", pairs.get(0).getValue0().getNumber());
        Assert.assertTrue(pairs.get(0).getValue1() == null);
    }
}