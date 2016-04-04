package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.command.CommandExecutor;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.Locale;

/**
 * Created by Golem765 on 22.03.2016.
 */
public class CashMachine
{
    public static final String RESOURCE_PATH = "com.javarush.test.level26.lesson15.big01.resources.";
    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        Operation current;
        try
        {
            CommandExecutor.execute(Operation.LOGIN);
            while(true)
            {
                current = ConsoleHelper.askOperation();
                CommandExecutor.execute(current);
            }
        }
        catch (InterruptOperationException ios)
        {
            ConsoleHelper.printExitMessage();
        }
    }
}
