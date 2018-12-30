package cn.xjiangwei.autoaudio;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        scheduleJob();


        LinkedList<String> audio_data = new LinkedList<>(Arrays.asList("媒体音量", "关闭", "打开"));
        audio_pick.attachDataSource(audio_data);

        LinkedList<String> ring_data = new LinkedList<>(Arrays.asList("铃声音量", "关闭", "打开"));
        ring_pick.attachDataSource(ring_data);

        LinkedList<String> clock_data = new LinkedList<>(Arrays.asList("闹铃音量", "关闭", "打开"));
        clock_pick.attachDataSource(clock_data);

        LinkedList<String> time_data = new LinkedList<>(Arrays.asList("作用时间", "0.5小时", "1小时", "1.5小时", "2小时", "2.5小时", "3小时", "3.5小时", "4小时", "4.5小时"));
        time_pick.attachDataSource(time_data);
    }


    // 当用户单击SCHEDULE JOB时执行。
    public void scheduleJob() {
        //开始配置JobInfo
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobService.class.getName()));
        //设置开机启动
        builder.setPersisted(true);        //设置失败后重试间隔时间和策略
        builder.setRequiresDeviceIdle(true);        //设置任务的周期性
        builder.setMinimumLatency(0);
        builder.setPeriodic(6 * 1000);
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
        Toast.makeText(this, "设置成功！", Toast.LENGTH_SHORT).show();
    }
}
