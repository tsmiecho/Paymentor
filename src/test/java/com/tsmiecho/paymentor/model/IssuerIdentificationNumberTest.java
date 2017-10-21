package com.tsmiecho.paymentor.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class IssuerIdentificationNumberTest {

    @Test
    public void shouldNotBeEmpty(){
        //given
        String rawNumber = "";

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new IssuerIdentificationNumber(rawNumber));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(IssuerIdentificationNumber.EMPTY_MESSAGE);
    }

    @Test
    public void shouldNotBeOnlyWhiteSpaces(){
        //given
        String rawNumber = "   ";

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new IssuerIdentificationNumber(rawNumber));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(IssuerIdentificationNumber.EMPTY_MESSAGE);
    }

    @Test
    public void shouldNotAllowIllegalCharacter(){
        //given
        String illegalCharacter = "J";

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new IssuerIdentificationNumber(illegalCharacter));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(IssuerIdentificationNumber.ILLEGAL_CHARACTER_MESSAGE);
    }

    @Test
    public void shouldBeCreatedWithCorrectCharacters(){
        //given
        String validNumber = "441712";

        //when
        final IssuerIdentificationNumber issuerIdentificationNumber = new IssuerIdentificationNumber(validNumber);

        //then
        assertThat(issuerIdentificationNumber.getNumber()).isEqualTo(validNumber);
    }
}