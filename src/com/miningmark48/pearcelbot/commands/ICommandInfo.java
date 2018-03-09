package com.miningmark48.pearcelbot.commands;

public interface ICommandInfo {

    String getName();
    String getDesc();
    String getUsage();
    CommandType getType();

}
