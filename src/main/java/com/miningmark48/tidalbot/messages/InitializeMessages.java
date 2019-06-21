package com.miningmark48.tidalbot.messages;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InitializeMessages {

    public static void init(MessageReceivedEvent event){

        //Init
        MessageGreetings.init(event);
        MessageReplies.init(event);
        MessageGeneral.init(event);

    }

}
