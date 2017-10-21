package com.tsmiecho.paymentor.repository;

import com.tsmiecho.paymentor.model.Bank;
import com.tsmiecho.paymentor.model.IssuerIdentificationNumber;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
@Repository
public class IssuerIdentificationNumberRangeRepository {

    Map<Pattern, Bank> dbInMemory = new HashMap<>();

    {
        dbInMemory.put(Pattern.compile("(^53(5[8-9])|(6[0-9])|(7[0-9])|(8[0-9])|(9[0-9]))|^54"), new Bank(2L, "RolBank"));
        dbInMemory.put(Pattern.compile("^52"), new Bank(2L, "nBank"));
        dbInMemory.put(Pattern.compile("^60|^61"), new Bank(3L, "Kakao SA"));
        dbInMemory.put(Pattern.compile("^53(0[0-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-9])|(5[0-7])"), new Bank(4L, "OMG Bank Łódzki"));
    }

    public Bank findByIssuerIdentificationNumber(IssuerIdentificationNumber inn) throws NoSuchBankException {
        return dbInMemory.keySet()
                .stream()
                .filter(p -> p.matcher(inn.getNumber()).find())
                .map(p -> dbInMemory.get(p))
                .findAny()
                .orElseThrow(() -> new NoSuchBankException("No such bank for "+inn));
    }
}
