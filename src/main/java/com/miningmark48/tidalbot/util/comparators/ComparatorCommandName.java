package com.miningmark48.tidalbot.util.comparators;

import com.miningmark48.tidalbot.base.ICommand;

import java.util.Comparator;

public class ComparatorCommandName implements Comparator<ICommand> {

    @Override
    public int compare(ICommand a, ICommand b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }

}
