package cn.xjiangwei.autoaudio;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xjiangwei.autoaudio.Tools.Check;
import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;
import cn.xjiangwei.autoaudio.service.JobService;

public class StartActivity extends AppCompatActivity {


    @BindView(R.id.audio_value)
    SeekBar audio;
    @BindView(R.id.ring_value)
    SeekBar ring;
    @BindView(R.id.clock_value)
    SeekBar clock;
    @BindView(R.id.time)
    SeekBar time;


    int audio_value, ring_valud, clock_value, time_value = 0;
    int audio_max, ring_max, clock_max;

    AudioManager audioManager;
    @BindView(R.id.audio_txt)
    TextView audioTxt;
    @BindView(R.id.ring_txt)
    TextView ringTxt;
    @BindView(R.id.clock_txt)
    TextView clockTxt;
    @BindView(R.id.time_txt)
    TextView timeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        scheduleJob();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        init();


    }

    private void init() {
        int current = 1;

        audio_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audio_value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audio.setMax(audio_max);
        audio.setProgress(audio_value);
        audioTxt.setText(String.valueOf((int) (((float) audio_value) / audio_max * 100)) + "%");


        ring_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        ring_valud = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        ring.setMax(ring_max);
        ring.setProgress(ring_valud);
        ringTxt.setText(String.valueOf((int) (((float) ring_valud) / ring_max * 100)) + "%");


        clock_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        clock_value = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        clock.setMax(clock_max);
        clock.setProgress(clock_value);
        clockTxt.setText(String.valueOf((int) (((float) clock_value) / clock_max * 100)) + "%");


        audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioTxt.setText(String.valueOf((int) (((float) i) / audio_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audio_value = seekBar.getProgress();
            }
        });


        ring.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ringTxt.setText(String.valueOf((int) (((float) i) / ring_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ring_valud = seekBar.getProgress();
            }
        });


        clock.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clockTxt.setText(String.valueOf((int) (((float) i) / clock_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                clock_value = seekBar.getProgress();
            }
        });

        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeTxt.setText(String.valueOf(i) + "分");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                time_value = seekBar.getProgress();
            }
        });

    }


    public void scheduleJob() {
        //开始配置JobInfo
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobService.class.getName()));
        //设置开机启动
        builder.setPersisted(true);        //设置失败后重试间隔时间和策略
        builder.setRequiresDeviceIdle(true);        //设置任务的周期性
        builder.setMinimumLatency(0);
        builder.setPeriodic(60 * 1000 * 15);
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // 这里就将开始在service里边处理我们配置好的job
        mJobScheduler.schedule(builder.build());

    }


    @OnClick(R.id.submit)
    public void onViewClicked() {


        Status.add(audio_value, ring_valud, clock_value, time_value);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audio_value, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, ring_valud, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, clock_value, 0);

        Toast.makeText(this, "设置成功！", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ruleList)
    public void go2RulesList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
