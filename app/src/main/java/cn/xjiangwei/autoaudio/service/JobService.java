package cn.xjiangwei.autoaudio.service;

import android.app.job.JobParameters;
import android.content.Context;
import android.media.AudioManager;
import cn.xjiangwei.autoaudio.Tools.Check;

public class JobService extends android.app.job.JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        System.out.println("Run");

        //audi, ring ,clock
        int[] conf = Check.checkNow();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, conf[0], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, conf[1], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, conf[2], 0);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
