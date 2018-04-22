package com.miningmark48.pearcelbot.commands.base;

public interface ICommandInfo {

    String getName();
    String getDesc();
    String getUsage();
    CommandType getType();

}
