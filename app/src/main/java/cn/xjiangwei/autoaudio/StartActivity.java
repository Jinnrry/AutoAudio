package cn.xjiangwei.autoaudio;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;
import org.litepal.LitePal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xjiangwei.autoaudio.Tools.Check;
import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;
import cn.xjiangwei.autoaudio.service.JobService;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.audio_pick)
    NiceSpinner audio_pick;
    @BindView(R.id.ring_pick)
    NiceSpinner ring_pick;
    @BindView(R.id.clock_pick)
    NiceSpinner clock_pick;

    @BindView(R.id.time)
    NiceSpinner time_pick;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.lable1)
    TextView lable1;

    @BindView(R.id.audio_info)
    TextView audio_info;
    @BindView(R.id.ring_info)
    TextView ring_info;
    @BindView(R.id.clock_info)
    TextView clock_info;
    @BindView(R.id.nowRule)
    TextView nowRule;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        scheduleJob();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        LinkedList<String> audio_data = new LinkedList<>(Arrays.asList("媒体音量", "关闭", "打开"));
        audio_pick.attachDataSource(audio_data);

        LinkedList<String> ring_data = new LinkedList<>(Arrays.asList("铃声音量", "关闭", "打开"));
        ring_pick.attachDataSource(ring_data);

        LinkedList<String> clock_data = new LinkedList<>(Arrays.asList("闹铃音量", "关闭", "打开"));
        clock_pick.attachDataSource(clock_data);

        LinkedList<String> time_data = new LinkedList<>(Arrays.asList("作用时间", "0.5小时", "1小时", "1.5小时", "2小时", "2.5小时", "3小时", "3.5小时", "4小时", "4.5小时"));
        time_pick.attachDataSource(time_data);


        updateStatus();





    }

    private void updateStatus() {
        int max = 1;
        int current = 1;
        int tmpstatus[] = Status.getStatus();
        if (tmpstatus[0] != 0) {
            audio_info.setText("媒体音量：" + Rules.STATUS[tmpstatus[0]]);
        } else {
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audio_info.setText("媒体音量：" + current / max * 100);
        }
        if (tmpstatus[1] != 0) {
            ring_info.setText("铃声音量：" + Rules.STATUS[tmpstatus[0]]);
        } else {
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            current = audioManager.getStreamVolume(AudioManager.STREAM_RING);
            ring_info.setText("铃声音量：" + current / max * 100);
        }
        if (tmpstatus[2] != 0) {
            clock_info.setText("闹钟音量：" + Rules.STATUS[tmpstatus[0]]);
        } else {
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            current = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
            clock_info.setText("闹钟音量：" + current / max * 100);
        }
        nowRule.setText(Check.getNowRule());
    }


    // 当用户单击SCHEDULE JOB时执行。
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
        int audio = audio_pick.getSelectedIndex();
        int ring = ring_pick.getSelectedIndex();
        int clock = clock_pick.getSelectedIndex();
        int time = time_pick.getSelectedIndex();
        Status.add(audio, ring, clock, time);


        //audi, ring ,clock
        int[] conf = Check.checkNow();
        switch (conf[0]) {
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                break;
        }


        switch (conf[1]) {
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
                break;
        }

        switch (conf[2]) {
            case Rules.CLOSE:
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
                break;
            case Rules.OPEN:
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 100, 0);
                break;
        }

        Toast.makeText(this, "设置成功！", Toast.LENGTH_SHORT).show();
        updateStatus();
    }

    @OnClick(R.id.ruleList)
    public void go2RulesList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
