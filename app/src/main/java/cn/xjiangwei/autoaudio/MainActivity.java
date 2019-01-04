package cn.xjiangwei.autoaudio;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;
import org.litepal.LitePal;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xjiangwei.autoaudio.db.Rules;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    AudioManager audioManager;
    int iaudio_value = 0;
    int iring_value = 0;
    int iclock_value = 0;
    int audio_max, ring_max, clock_max;


    private NormalRecyclerViewAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LitePal.initialize(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        ring_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        clock_max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);

        initData();
        initView();
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);


    }


    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new NormalRecyclerViewAdapter(Rules.getList(audio_max, ring_max, clock_max));
        mAdapter.setOnItemClickListener(new NormalRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(long id) {

                delRule(id);
            }

            @Override
            public void onLongClick(long position) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_rule:
                addRule();
                break;
            default:
                about();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void about() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("About");
        builder.setMessage("这是一个完全开源免费的软件！\n Github:https://github.com/jiangwei1995910/AutoAudio");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("提建议", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/jiangwei1995910/AutoAudio/issues/new");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        builder.show();
    }


    private void initView() {
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }


    private void addRule() {
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.form, null);
        customizeDialog.setView(dialogView);
        Resources res = getResources();
        final NiceSpinner year = (NiceSpinner) dialogView.findViewById(R.id.year);
        year.attachDataSource(Arrays.asList(res.getStringArray(R.array.year)));
        final NiceSpinner month = (NiceSpinner) dialogView.findViewById(R.id.month);
        month.attachDataSource(Arrays.asList(res.getStringArray(R.array.month)));
        final NiceSpinner day = (NiceSpinner) dialogView.findViewById(R.id.day);
        day.attachDataSource(Arrays.asList(res.getStringArray(R.array.day)));
        final NiceSpinner week = (NiceSpinner) dialogView.findViewById(R.id.week);
        week.attachDataSource(Arrays.asList(res.getStringArray(R.array.week)));
        final EditText hour = (EditText) dialogView.findViewById(R.id.hour);
        final EditText min = (EditText) dialogView.findViewById(R.id.min);
        final EditText end_hour = (EditText) dialogView.findViewById(R.id.end_hour);
        final EditText end_min = (EditText) dialogView.findViewById(R.id.end_min);
        final SeekBar audio_value = (SeekBar) dialogView.findViewById(R.id.form_audio_value);
        audio_value.setMax(audio_max);
        final SeekBar ring_value = (SeekBar) dialogView.findViewById(R.id.form_ring_value);
        ring_value.setMax(ring_max);
        final SeekBar clock_value = (SeekBar) dialogView.findViewById(R.id.form_clock_value);
        clock_value.setMax(clock_max);
        final TextView audio_txt = (TextView) dialogView.findViewById(R.id.form_audio_txt);
        final TextView ring_txt = (TextView) dialogView.findViewById(R.id.form_ring_txt);
        final TextView clock_txt = (TextView) dialogView.findViewById(R.id.form_clock_txt);

        audio_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audio_txt.setText(String.valueOf((int) (((float) i) / audio_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iaudio_value = seekBar.getProgress();
            }
        });


        ring_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ring_txt.setText(String.valueOf((int) (((float) i) / ring_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iring_value = seekBar.getProgress();
            }
        });

        clock_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clock_txt.setText(String.valueOf((int) (((float) i) / clock_max * 100)) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iclock_value = seekBar.getProgress();
            }
        });

        customizeDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int iyear = year.getSelectedIndex() + 2017;
                iyear = iyear == 2017 ? -1 : iyear;
                int imonth = month.getSelectedIndex();
                imonth = imonth == 0 ? -1 : imonth;
                int iday = day.getSelectedIndex();
                iday = iday == 0 ? -1 : iday;
                int iweek = week.getSelectedIndex();
                iweek = iweek == 0 ? -1 : iweek;
                int ihour, imin, iend_hour, iend_min;
                try {
                    ihour = Integer.parseInt(hour.getText().toString());
                } catch (Exception e) {
                    ihour = -1;
                }
                try {
                    imin = Integer.parseInt(min.getText().toString());
                } catch (Exception e) {
                    imin = -1;
                }
                try {
                    iend_hour = Integer.parseInt(end_hour.getText().toString());
                } catch (Exception e) {
                    iend_hour = -1;
                }
                try {
                    iend_min = Integer.parseInt(end_min.getText().toString());
                } catch (Exception e) {
                    iend_min = -1;
                }
                Rules.addRules(iyear, imonth, iday, ihour, imin, iend_hour, iend_min, iweek, iaudio_value, iring_value, iclock_value);
                mAdapter.updateData(Rules.getList(audio_max, ring_max, clock_max));
            }
        });
        customizeDialog.show();
    }


    private void delRule(final long id) {

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("确认");
        normalDialog.setMessage("你是否要删除这个规则?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        LitePal.find(Rules.class, id).delete();
                        mAdapter.updateData(Rules.getList(audio_max, ring_max, clock_max));
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

}
