package com.miningmark48.tidalbot.util.features;

import com.miningmark48.tidalbot.util.UtilLogger;

public class Clock {

    public static int uptimeSeconds = 50;
    public static int uptimeMinutes = 59;
    public static int uptimeHours = 23;
    public static int uptimeDays = 0;

    public static void runClockUptime(){
        Thread thread = new Thread(() -> {

            UtilLogger.log(UtilLogger.LogType.STATUS, "Uptime clock started!");

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
