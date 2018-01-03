package com.miningmark48.pearcelbot.util;

import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.logging.Logger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.Random;

public class Clock {

    public static String sourcePrev = "";
    public static boolean isPlaying = false;
//    public static AudioSource sourceCurrent;

    public static int tempNum = 0;

    public static int timeoutSeconds = 30;

    public static int uptimeSeconds = 50;
    public static int uptimeMinutes = 59;
    public static int uptimeHours = 23;
    public static int uptimeDays = 0;

    public static void runClockGame(JDA jda){
        Thread thread = new Thread() {
            @Override
            public void run() {

                while (true){

                    Random rand = new Random();
                    int randAmount = 3;
                    int randNum = rand.nextInt(randAmount);
                    if(tempNum == randNum){
                        randNum = rand.nextInt(randAmount);
                    }

                    switch (randNum){
                        default:
                        case 0:
                            jda.getPresence().setGame(Game.of("Do " + Reference.botCommandKey + "cmds"));
                            tempNum = 0;
                            break;
                        case 1:
                            jda.getPresence().setGame(Game.of("Pearcel Mod"));
                            tempNum = 1;
                            break;
                        case 2:
                            jda.getPresence().setGame(Game.of("sick music."));
                            tempNum = 2;
                            break;
                        case 3:
                            jda.getPresence().setGame(Game.of("The Banjo."));
                            tempNum = 3;
                            break;
                    }

                    try {
                        Thread.sleep(timeoutSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        };

        thread.start();
    }

    public static void runClockUptime(JDA jda){
        Thread thread = new Thread() {
            @Override
            public void run() {

                Logger.log("status", "Uptime clock started!");

                uptimeSeconds = 0;
                uptimeMinutes = 0;
                uptimeHours = 0;
                uptimeDays = 0;

                while (true) {

                    uptimeSeconds += 1;
                    uptimeMinutes = uptimeMinutes + uptimeSeconds / 60;
                    uptimeHours = uptimeHours + uptimeMinutes / 60;
                    uptimeDays = uptimeDays + uptimeHours / 24;

                    if(uptimeSeconds == 60){
                        uptimeSeconds = 0;
                    }

                    if(uptimeMinutes == 60){
                        uptimeMinutes = 0;
                    }

                    if(uptimeHours == 24){
                        uptimeHours = 0;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        thread.start();
    }


}
