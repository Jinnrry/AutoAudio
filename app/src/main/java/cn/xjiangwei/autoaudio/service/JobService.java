package cn.xjiangwei.autoaudio.service;

import android.app.job.JobParameters;
import android.content.Context;
import android.media.AudioManager;

import java.util.Arrays;

import cn.xjiangwei.autoaudio.Tools.Check;
import cn.xjiangwei.autoaudio.db.Rules;

public class JobService extends android.app.job.JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {


        //audi, ring ,clock
        int[] conf = Check.checkNow();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        switch (conf[0]){
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                break;
        }


        switch (conf[1]){
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
                break;
        }

        switch (conf[2]){
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 100, 0);
                break;
        }


        System.out.println(Arrays.toString(conf));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
