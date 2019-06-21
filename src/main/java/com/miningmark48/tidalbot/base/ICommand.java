package com.miningmark48.tidalbot.base;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    //Info
    String getName();
    String getDesc();
    String getUsage();
    default boolean isMusic() { return false; }
    default EnumRestrictions getPermissionRequired() { return EnumRestrictions.REGULAR; }
    default Permission[] getPermissions() { return null; }

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);


}
