package com.miningmark48.tidalbot.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class UtilUserSelection {

    public static User getUserByName(Guild guild, String name) {
        Member member = guild.getMembersByName(name, true).stream().findFirst().orElse(null);
        return member != null ? member.getUser() : null;

    }

    public static User getUserByNick(Guild guild, String name) {
        Member member = guild.getMembersByNickname(name, true).stream().findFirst().orElse(null);
        return member != null ? member.getUser() : null;
    }

    public static User getUserByTag(Guild guild, String name) {
        Member member = guild.getMemberByTag(name);
        return member != null ? member.getUser() : null;
    }

    public static User getUserByID(Guild guild, String name) {
        Member member = guild.getMemberById(name);
        return member != null ? member.getUser() : null;
    }

    public static User getUserByMention(Message msg) {
        Member member = msg.getMentionedMembers().stream().findFirst().orElse(null);
        return member != null ? member.getUser() : null;
    }

    @SuppressWarnings("unused")
    public static User getUserByAny(Guild guild, String name, Message msg) {
        User byMention = getUserByMention(msg);
        if (byMention != null) return byMention;

        return getUserByAny(guild, name);
    }

    public static User getUserByAny(Guild guild, String name) {
        User byName = getUserByName(guild, name);
        if (byName != null) return byName;

        User byNick = getUserByNick(guild, name);
        if (byNick != null) return byNick;

        User byTag = getUserByTag(guild, name);
        if (byTag != null) return byTag;

        User byID = getUserByID(guild, name);
        if (byID != null) return byID;

        return null;

    }

}
