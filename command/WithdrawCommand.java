package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;
import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


/**
 * Created by Golem765 on 26.03.2016.
 */
class WithdrawCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw");
    @Override
    public void execute()throws InterruptOperationException//сделать смену кода валюты
    {
        boolean currency = false;
        CurrencyManipulator current = null;
        String currencyCode = null;
        String s = null;
        Map<Integer, Integer> withdrawing;
        int sum = 0;
        ConsoleHelper.writeMessage(res.getString("before"));
        while (!currency)
        {
            currencyCode = ConsoleHelper.askCurrencyCode();
            current = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
            ConsoleHelper.writeMessage(String.format(res.getString("confirm.currency"), currencyCode));
            s = ConsoleHelper.readString();
            if(s.equalsIgnoreCase(res.getString("yes")))
                currency = true;
        }
        ConsoleHelper.writeMessage(res.getString("specify.amount"));
        while(true)
        {
            s = ConsoleHelper.readString();
            if(s.equalsIgnoreCase(res.getString("break")))
                break;
            try
            {
                sum = Integer.parseInt(s);
                if(sum <= 0)
                    throw new NumberFormatException();

            }
            catch (NumberFormatException nfs)
            {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if(!current.isAmountAvailable(sum))
            {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try
            {
                withdrawing = new TreeMap(current.withdrawAmount(sum)).descendingMap();
                for (Map.Entry<Integer, Integer> me : withdrawing.entrySet())
                {
                    ConsoleHelper.writeMessage(String.format("\t%d - %d", me.getKey(), me.getValue()));
                }
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, currencyCode));
                break;
            }
            catch (NotEnoughMoneyException neme)
            {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }
}
