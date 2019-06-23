package com.miningmark48.tidalbot.util.comparators;

import com.miningmark48.tidalbot.base.ICommand;

import java.util.Comparator;

public class ComparatorCommandType implements Comparator<ICommand> {

    @Override
    public int compare(ICommand a, ICommand b) {
        return a.getPermissionRequired().getName().compareToIgnoreCase(b.getPermissionRequired().getName());
    }

}
