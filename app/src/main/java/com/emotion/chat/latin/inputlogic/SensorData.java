package com.emotion.chat.latin.inputlogic;

import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;

import java.util.Random;

public class SensorData {

    private Context context;
    public static float size;

    public int getBrightness() {
        return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS,-1);
    }

    public static float getPressure() {
        Random r = new Random();
        return r.nextFloat() * 30;
    }

    public void setSize(float size) {
        SensorData.size = size;
    }

    public float getSize() {
        return size;
    }
}
