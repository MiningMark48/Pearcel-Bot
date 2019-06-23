package com.miningmark48.tidalbot.base;

import javax.annotation.Nonnull;

public enum EnumRestrictions {

    REGULAR("Regular"),
    MANAGER("Manager"),
    ADMIN("Administrator"),
    OWNER("Server Owner"),
    SPECIFIC("Specific"),
    BOT_OWNER("Bot Owner");

    private final String name;

    EnumRestrictions(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getName()
    {
        return this.name;
    }

}
