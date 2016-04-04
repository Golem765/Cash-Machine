package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Golem765 on 27.03.2016.
 */
public class LoginCommand implements Command
{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login");
    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("specify.data"));
        while(true)
        {
            String s = ConsoleHelper.readString();
            String epin = ConsoleHelper.readString();
            if(s.length()!=12||epin.length()!=4)
            {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }
            if(validCreditCards.containsKey(s)&&validCreditCards.getString(s).equalsIgnoreCase(epin))
            {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), s));
                break;
            }
            else
            {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), s));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        }
    }
}
