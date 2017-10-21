package com.tsmiecho.paymentor.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by Tomasz Śmiechowicz on 20.10.17.
 */
public class PrimaryAccountNumberTest {

    @Test
    public void shouldNotBeEmpty(){
        //given
        final String rawNumber = "";
        final int iinSize = 6;

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new PrimaryAccountNumber(rawNumber, iinSize));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PrimaryAccountNumber.EMPTY_MESSAGE);
    }

    @Test
    public void shouldNotBeOnlyWhiteSpaces(){
        //given
        final String rawNumber = "   ";
        final int iinSize = 6;

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new PrimaryAccountNumber(rawNumber, iinSize));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PrimaryAccountNumber.EMPTY_MESSAGE);
    }

    @Test
    public void shouldNotAllowIllegalCharacter(){
        //given
        final String illegalCharacter = "J";
        final int iinSize = 6;

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new PrimaryAccountNumber(illegalCharacter, iinSize));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PrimaryAccountNumber.ILLEGAL_CHARACTER_MESSAGE);
    }

    @Test
    public void shouldNotAllowTooBigInnSize(){
        //given
        final String validNumber = "4417123456789113";
        final int illegalIinSize = 17;

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new PrimaryAccountNumber(validNumber, illegalIinSize));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PrimaryAccountNumber.ILLEGAL_ISSUER_IDENTIFICATION_SIZE_MESSAGE);
    }

    @Test
    public void shouldNotAllowTooSmallInnSize(){
        //given
        final String validNumber = "4417123456789113";
        final int illegalIinSize = PrimaryAccountNumber.MINIMAL_ISSUER_IDENTIFICATION_NUMBER_SIZE - 1;

        //when
        Throwable numberCantBeEmpty = catchThrowable(() -> new PrimaryAccountNumber(validNumber, illegalIinSize));

        //then
        assertThat(numberCantBeEmpty)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PrimaryAccountNumber.ILLEGAL_ISSUER_IDENTIFICATION_SIZE_MESSAGE);
    }
    @Test
    public void shouldBeCreatedWithCorrectCharacters(){
        //given
        final String validNumber = "4417123456789113";
        final int iinSize = 6;

        //when
        final PrimaryAccountNumber primaryAccountNumber = new PrimaryAccountNumber(validNumber, iinSize);

        //then
        assertThat(primaryAccountNumber.getIssuerIdentificationNumber().getNumber()).isEqualTo(validNumber.substring(0, 6));
        assertThat(primaryAccountNumber.getIndividualAccountIdentifier()).isEqualTo(validNumber.substring(6, validNumber.length()));
    }


}