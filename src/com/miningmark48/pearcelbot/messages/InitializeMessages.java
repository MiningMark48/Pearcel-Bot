package com.miningmark48.pearcelbot.messages;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InitializeMessages {

    public static void init(MessageReceivedEvent event){

        //Init
        MessageGreetings.init(event);
        MessageReplies.init(event);
        MessageGeneral.init(event);

    }

}
