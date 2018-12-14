package com.arcsoft.arcfacedemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.arcsoft.face.FaceEngine;

public class ConfigUtil {
    private static final String APP_NAME = "ArcFaceDemo";
    private static final String TRACK_ID = "trackID";
    private static final String FT_ORIENT = "ftOrient";

    public static void setTrackId(Context context, int trackId) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(TRACK_ID, trackId)
                .apply();
    }

    public static int getTrackId(Context context){
        if (context == null){
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TRACK_ID,0);
    }
    public static void setFtOrient(Context context, int ftOrient) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(FT_ORIENT, ftOrient)
                .apply();
    }

    public static int getFtOrient(Context context){
        if (context == null){
            return FaceEngine.ASF_OP_270_ONLY;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FT_ORIENT, FaceEngine.ASF_OP_270_ONLY);
    }
}
