package com.miningmark48.pearcelbot.richpresence;

import com.miningmark48.pearcelbot.util.Logger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.Random;

public class PresenceClock {

    private static int timeoutSeconds = 15;
    private static int prevNumCat;

    public static void runClockGame(JDA jda){
        Thread thread = new Thread(() -> {

            while (true){

                Random rand = new Random();

                int randType = rand.nextInt(3);

                while (randType == prevNumCat) {
                    randType = rand.nextInt(3);
                }

                Logger.log(Logger.LogType.DEBUG, randType + "");

                prevNumCat = randType;

                int randPresence;

                switch (randType) {
                    default:
                    case 0:
                        randPresence = rand.nextInt(PresenceReference.playingPresence.length - 1);
                        jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, PresenceReference.playingPresence[randPresence]));
                        break;
                    case 1:
                        randPresence = rand.nextInt(PresenceReference.listeningPresence.length - 1);
                        jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, PresenceReference.listeningPresence[randPresence]));
                        break;
                    case 2:
                        randPresence = rand.nextInt(PresenceReference.watchingPresence.length - 1);
                        jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, PresenceReference.watchingPresence[randPresence]));
                        break;
                }

                try {
                    Thread.sleep(timeoutSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });

        thread.start();
    }

}
