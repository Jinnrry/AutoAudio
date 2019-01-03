package cn.xjiangwei.autoaudio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;


import cn.xjiangwei.autoaudio.Tools.Check;

public class VolumeChangeBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    AudioManager audioManager;


    @Override
    public void onReceive(Context context, Intent intent) {
//        if (audioManager == null) {
//            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        }
//        updateVolume();
//        Intent i = new Intent();
//        i.setClassName("cn.xjiangwei.autoaudio", "cn.xjiangwei.autoaudio.StartActivity");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
    }


    private void updateVolume() {


        int max, current, audio, ring, clock;
        max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (current == max) {
            audio = 2;
        } else if (current == 0) {
            audio = 1;
        } else {
            audio = 3;
        }


        max = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        current = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        if (current == max) {
            ring = 2;
        } else if (current == 0) {
            ring = 1;
        } else {
            ring = 3;
        }

        max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        current = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        if (current == max) {
            clock = 2;
        } else if (current == 0) {
            clock = 1;
        } else {
            clock = 3;
        }

        int[] custor = Check.checkNow();

        if ((custor[0] != 0 && custor[0] != audio)
                || (custor[1] != 0 && custor[1] != ring)
                || (custor[2] != 0 && custor[2] != clock)
                ) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, custor[0] == 2 ? 100 : 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, custor[1] == 2 ? 100 : 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, custor[2] == 2 ? 100 : 0, 0);
        }


    }


}
