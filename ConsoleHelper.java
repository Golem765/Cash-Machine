package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Created by Golem765 on 22.03.2016.
 */
public class ConsoleHelper
{
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common");
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message)
    {
        System.out.println(message);
    }

    public static void printExitMessage()
    {
        writeMessage(res.getString("the.end"));
    }

    public static String readString()throws InterruptOperationException
    {
        String message = "";
        try
        {
            message = reader.readLine();
            if(message.equalsIgnoreCase("exit"))
                throw new InterruptOperationException();
        }
        catch (IOException ignored)
        {
        }
        return message;
    }

    public static String askCurrencyCode()throws InterruptOperationException
    {
        writeMessage(res.getString("choose.currency.code"));
        String ret = readString();
        while(!(ret.length()==3))
        {
            writeMessage(res.getString("invalid.data"));
            ret = readString();
        }
        return ret.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode)throws InterruptOperationException
    {
        String[] array;
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));

        while (true)
        {
            String s = readString();
            array = s.split(" ");
            int k;
            int l;
            try
            {
                k = Integer.parseInt(array[0]);
                l = Integer.parseInt(array[1]);
            }
            catch (Exception e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            if (k <= 0 || l <= 0 || array.length > 2)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return array;
    }

    public static Operation askOperation()throws InterruptOperationException
    {
        writeMessage(res.getString("choose.operation")+ "\n1 for "
                + res.getString("operation.INFO") + "\n2 for "
                + res.getString("operation.DEPOSIT") + " \n3 for "
                + res.getString("operation.WITHDRAW") + "\n4 for "
                + res.getString("operation.EXIT"));
        Operation ret = null;
        boolean success = false;
        while (!success)
        {
            try
            {
                ret = Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));
                success = true;
            }
            catch (IllegalArgumentException e)
            {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return ret;
    }


}
