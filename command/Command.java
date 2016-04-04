package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

/**
 * Created by Golem765 on 26.03.2016.
 */
interface Command
{
    void execute()throws InterruptOperationException;
}
