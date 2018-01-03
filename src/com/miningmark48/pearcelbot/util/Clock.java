package com.miningmark48.pearcelbot.util;

import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.logging.Logger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.Random;

public class Clock {

    private static int timeoutSeconds = 15;
    private static int prevNum = 0;
    private static final String[] games = {"Do " + Reference.botCommandKey + "cmds", "Pearcel Mod", "sick music", "the banjo", "with your mom", "Minecraft", "Overwatch"};

    public static int uptimeSeconds = 50;
    public static int uptimeMinutes = 59;
    public static int uptimeHours = 23;
    public static int uptimeDays = 0;

    public static void runClockGame(JDA jda){
        Thread thread = new Thread(() -> {

            while (true){

                Random rand = new Random();
                int randAmount = games.length;
                int randNum = rand.nextInt(randAmount);

                while (prevNum == randNum) {
                    randNum = rand.nextInt(randAmount);
                }

                jda.getPresence().setGame(Game.of(games[randNum]));

                prevNum = randNum;

                try {
                    Thread.sleep(timeoutSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });

        thread.start();
    }

    public static void runClockUptime(){
        Thread thread = new Thread(() -> {

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

                if (uptimeSeconds == 60) {
                    uptimeSeconds = 0;
                }

                if (uptimeMinutes == 60) {
                    uptimeMinutes = 0;
                }

                if (uptimeHours == 24) {
                    uptimeHours = 0;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        thread.start();
    }


}
