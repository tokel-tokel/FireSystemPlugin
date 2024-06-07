package ru.hse.edu.stalivanov.fireplugin.storage;

import java.util.NavigableSet;

public interface Util
{
    static int getFreeInSet(NavigableSet<Integer> set)
    {
        if(set.first() == null)
            return 1;
        int first = set.first();
        Integer second = set.higher(first);
        while(second != null && second - first <= 1)
        {
            first = second;
            second = set.higher(first);
        }
        return first + 1;
    }
}
