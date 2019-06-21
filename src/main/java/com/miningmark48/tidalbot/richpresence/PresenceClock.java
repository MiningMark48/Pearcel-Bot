package com.miningmark48.tidalbot.richpresence;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

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

                prevNumCat = randType;

                int randPresence;

                switch (randType) {
                    default:
                    case 0:
                        randPresence = rand.nextInt(PresenceReference.playingPresence.length - 1);
                        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, PresenceReference.playingPresence[randPresence]));
                        break;
                    case 1:
                        randPresence = rand.nextInt(PresenceReference.listeningPresence.length - 1);
                        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, PresenceReference.listeningPresence[randPresence]));
                        break;
                    case 2:
                        randPresence = rand.nextInt(PresenceReference.watchingPresence.length - 1);
                        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, PresenceReference.watchingPresence[randPresence]));
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
