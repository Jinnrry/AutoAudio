package cn.xjiangwei.autoaudio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;


import cn.xjiangwei.autoaudio.Tools.Check;

public class VolumeChangeBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    AudioManager audioManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        updateVolume();
        Toast.makeText(context, "请在智能音量中设置音量！", Toast.LENGTH_SHORT).show();
    }


    private void updateVolume() {


        int audio, ring, clock;
        audio = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        clock = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        ring = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int[] custor = Check.checkNow();

        if (custor[0] == audio && custor[1] == ring && custor[2] == clock) {
            return;
        }

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, custor[0], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, custor[1], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, custor[2], 0);


    }


}
