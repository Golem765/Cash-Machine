package com.javarush.test.level26.lesson15.big01;

/**
 * Created by Golem765 on 22.03.2016.
 */
public enum Operation
{
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i)throws IllegalArgumentException
    {
        if(i<1||i>4)
            throw new IllegalArgumentException();
        return INFO.ordinal()==i ? INFO : DEPOSIT.ordinal() == i ? DEPOSIT : WITHDRAW.ordinal() == i ? WITHDRAW : EXIT;
    }
}
