package cn.xjiangwei.autoaudio;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.service.JobService;
import cn.xjiangwei.autoaudio.vo.Item;

import static android.app.job.JobInfo.BACKOFF_POLICY_LINEAR;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private NormalRecyclerViewAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LitePal.initialize(this);
        initData();
        initView();
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);




    }




    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new NormalRecyclerViewAdapter(Rules.getList());
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }


    private void addRule() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.form, null);
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int iMin = 0;
                int year = ((Spinner) dialogView.findViewById(R.id.year)).getSelectedItemPosition();
                int month = ((Spinner) dialogView.findViewById(R.id.month)).getSelectedItemPosition();
                int day = ((Spinner) dialogView.findViewById(R.id.day)).getSelectedItemPosition();
                int hour = ((Spinner) dialogView.findViewById(R.id.hour)).getSelectedItemPosition();
                String min = ((EditText) dialogView.findViewById(R.id.min)).getText().toString();
                int week = ((Spinner) dialogView.findViewById(R.id.week)).getSelectedItemPosition();
                int audio = ((Spinner) dialogView.findViewById(R.id.audio)).getSelectedItemPosition();
                int ring = ((Spinner) dialogView.findViewById(R.id.ring)).getSelectedItemPosition();
                int clock = ((Spinner) dialogView.findViewById(R.id.clock)).getSelectedItemPosition();
                if (year != 0) {
                    year += 2017;
                }
                try {
                    iMin = Integer.parseInt(min);
                } catch (Exception e) {
                    iMin = 0;
                }


                Rules.addRules(year, month, day, hour, iMin,23,59, week, audio, ring, clock);
                mAdapter.updateData(Rules.getList());


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
                        mAdapter.updateData(Rules.getList());
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
