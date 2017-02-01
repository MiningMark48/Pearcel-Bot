package com.miningmark48.pearcelbot.messages;

import com.miningmark48.pearcelbot.customcommands.GetCommand;
import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InitializeMessages {

    public static void init(MessageReceivedEvent event){

        //Init
        MessageGreetings.init(event);
        MessageReplies.init(event);
        MessageGeneral.init(event);

    }

}
