package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Golem765 on 26.03.2016.
 */
class DepositCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit");
    @Override
    public void execute()throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String curCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currentM = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        String[] add = ConsoleHelper.getValidTwoDigits(curCode);
        int den = Integer.parseInt(add[0]);
        int am = Integer.parseInt(add[1]);
        currentM.addAmount(den, am);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), den*am, curCode));
    }
}
