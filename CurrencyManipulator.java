package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * Created by Golem765 on 22.03.2016.
 */
public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer, Integer> denominations;
    private boolean hasMoney = false;

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
        denominations = new HashMap<Integer, Integer>();

    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void addAmount(int denomination, int count){
        if(denominations.containsKey(denomination))
            denominations.put(denomination, denominations.get(denomination) + count);
        else
            denominations.put(denomination,count);
        hasMoney = true;
    }

    public int getTotalAmount()
    {
        int ret = 0;
        for(Map.Entry<Integer, Integer> me : denominations.entrySet())
        {
            ret += me.getKey()*me.getValue();
        }
        return ret;
    }

    public boolean hasMoney()//изменить на return getTotalAmount>0
    {
        boolean result=true;
        if (denominations.isEmpty()) result = false;
        else {
            int zerosCount=0;
            for (Map.Entry<Integer,Integer> pair : denominations.entrySet()){
                if (pair.getValue()==0) zerosCount++;
            }
            if (zerosCount==denominations.size()) result=false;
        }
        return result;
    }

    public boolean isAmountAvailable(int expectedAmount)
    {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount)throws NotEnoughMoneyException
    {
        Map<Integer, Integer> denominationsCopy = new HashMap<>(denominations);
        ArrayList<Integer> denom = new ArrayList<>();
        denom.addAll(denominationsCopy.keySet());
        Collections.sort(denom, new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2-o1;
            }
        });
        Map<Integer, Integer> ret = new HashMap<>();
        int active;
        boolean success = false;
        Integer bad = 0;
        while(!success)
        {
            if(denom.isEmpty())
                throw new NotEnoughMoneyException();
            ret.clear();
            active = expectedAmount;
            for (Integer i : denom)
            {
                int den = i;
                if (active < den)
                    continue;
                bad = i;
                int am = denominationsCopy.get(i);
                int count = 0;
                while (active >= den && am > 0)
                {
                    active -= den;
                    am--;
                    count++;
                }
                ret.put(den, count);
                if (active == 0)
                {
                    success = true;
                    break;
                }
            }
            if(bad==0)
                throw new NotEnoughMoneyException();
            if(!success)
            {
                denominationsCopy.put(bad, denominationsCopy.get(bad) - 1);
                if (denominationsCopy.get(bad) == 0)
                    denom.remove(bad);
            }
        }
        for(Map.Entry<Integer, Integer> pair : ret.entrySet())
        {
            denominations.put(pair.getKey(), denominations.get(pair.getKey())-pair.getValue());
        }
        return ret;
    }

}
