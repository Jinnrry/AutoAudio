package cn.xjiangwei.autoaudio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;


import java.util.Arrays;

import cn.xjiangwei.autoaudio.Tools.Check;
import cn.xjiangwei.autoaudio.Tools.DebugLog;
import cn.xjiangwei.autoaudio.db.Status;

public class VolumeChangeBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    AudioManager audioManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        int audio_value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int ring_value = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int clock_value = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);

        int[] status=Check.checkNow();
        if (audio_value==status[0] && ring_value==status[1] && clock_value==status[2]){
            return;
        }

        Status.add(audio_value, ring_value, clock_value, 30);
        Toast.makeText(context, "音量改变，该音量值保留半小时，如需修改时间请到智能音量设置", Toast.LENGTH_SHORT).show();
    }


}
