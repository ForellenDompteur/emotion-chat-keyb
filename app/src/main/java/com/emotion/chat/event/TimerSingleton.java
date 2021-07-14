package com.emotion.chat.event;

import java.util.Random;
import java.util.Timer;

public class TimerSingleton {

    public static Timer timerBase;
    private long countInSeconds;


    public static int getElapsedTime() {
        Random r = new Random();
        return r.nextInt(10 - 1) + 1;
    }
}
