package com.tsmiecho.paymentor.repository;

import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class InitialRepositoryValuesTest {

    private IssuerIdentificationNumberRangeRepository repository = new IssuerIdentificationNumberRangeRepository();

    @Test
    public void testRolBank5358() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("5358"));
        Assert.assertEquals("RolBank", bank.getName());
    }

    @Test
    public void testRolBank54990() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("54990"));
        Assert.assertEquals("RolBank", bank.getName());
    }

    @Test
    public void testRolBank539911() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("539911"));
        Assert.assertEquals("RolBank", bank.getName());
    }

    @Test
    public void testRolBank535748() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("535748"));
        Assert.assertNotEquals("RolBank", bank.getName());
    }

    @Test
    public void testNBank618732() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("618732"));
        Assert.assertNotEquals("nBank", bank.getName());
    }

    @Test(expected = NoSuchBankException.class)
    public void testNBank510082() throws Exception {
        repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("510082"));
    }

    @Test
    public void testNBank5299() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("5299"));
        Assert.assertEquals("nBank", bank.getName());
    }

    @Test
    public void testNBank52() throws Exception {
        final Bank bank4 = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("52"));
        Assert.assertEquals("nBank", bank4.getName());
    }

    @Test(expected = NoSuchBankException.class)
    public void testKakaoSA668732() throws Exception {
        repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("668732"));
    }

    @Test(expected = NoSuchBankException.class)
    public void testKakaoSA510082() throws Exception {
        repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("510082"));
    }

    @Test
    public void testKakaoSA6099() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("6099"));
        Assert.assertEquals("Kakao SA", bank.getName());
    }

    @Test
    public void testKakaoSA61004567() throws Exception {
        final Bank bank4 = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("61004567"));
        Assert.assertEquals("Kakao SA", bank4.getName());
    }

    @Test(expected = NoSuchBankException.class)
    public void testOMGBankLodzki660732() throws Exception {
        repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("660732"));
    }

    @Test
    public void testOMGBankLodzki535882() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("535882"));
        Assert.assertNotEquals("OMG Bank Łódzki", bank.getName());
    }

    @Test
    public void testOMGBankLodzki5300() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("5300"));
        Assert.assertEquals("OMG Bank Łódzki", bank.getName());
    }

    @Test
    public void testOMGBankLodzki53574567() throws Exception {
        final Bank bank = repository.findByIssuerIdentificationNumber(new IssuerIdentificationNumber("53574567"));
        Assert.assertEquals("OMG Bank Łódzki", bank.getName());
    }
}