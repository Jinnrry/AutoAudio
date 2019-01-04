package cn.xjiangwei.autoaudio.Tools;

import android.util.Log;

public class DebugLog {
    private static final boolean DebugModel = true;
    private static final String TAG = "AutoAudio";

    public static void v(String tag, String info) {
        if (DebugModel)
            Log.v(tag, info);
    }

    public static void d(String tag, String info) {
        if (DebugModel)
            Log.d(tag, info);
    }

    public static void w(String tag, String info) {
        if (DebugModel)
            Log.w(tag, info);
    }

    public static void i(String tag, String info) {
        if (DebugModel)
            Log.i(tag, info);
    }

    public static void v(String info) {
        if (DebugModel)
            Log.v(TAG, info);
    }

    public static void d(String info) {
        if (DebugModel)
            Log.d(TAG, info);
    }

    public static void w(String info) {
        if (DebugModel)
            Log.w(TAG, info);
    }

    public static void i(String info) {
        if (DebugModel)
            Log.i(TAG, info);
    }

    public static void e(String tag, String info) {
        if (DebugModel)
            Log.e(tag, info);
    }

    public static void e(String info) {
        if (DebugModel)
            Log.e(TAG, info);
    }
}
