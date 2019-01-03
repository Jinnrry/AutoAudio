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

        System.out.println("Run");

        //audi, ring ,clock
        int[] conf = Check.checkNow();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        System.out.println(Arrays.toString(conf));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
