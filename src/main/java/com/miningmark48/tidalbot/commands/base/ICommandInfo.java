package com.miningmark48.tidalbot.commands.base;

public interface ICommandInfo {

    String getName();
    String getDesc();
    String getUsage();
    CommandType getType();

}
