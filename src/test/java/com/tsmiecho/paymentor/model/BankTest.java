package com.tsmiecho.paymentor.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class BankTest {

    @Test
    public void nameShouldNotBeEmpty(){
        //given
        long id = 1L;
        String rawName = "";

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new Bank(id, rawName));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Bank.EMPTY_MESSAGE);
    }
}
