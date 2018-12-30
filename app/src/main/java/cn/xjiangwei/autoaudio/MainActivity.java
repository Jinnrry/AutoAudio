package cn.xjiangwei.autoaudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.litepal.LitePal;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.vo.Item;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;



    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LitePal.initialize(this);
        initData();
        initView();

    }
    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new NormalRecyclerViewAdapter(Rules.getList());
    }

    private void initView() {
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }


}
