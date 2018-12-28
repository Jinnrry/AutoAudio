package cn.xjiangwei.autoaudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.litepal.LitePal;

import java.util.List;

import cn.xjiangwei.autoaudio.db.Rules;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.initialize(this);

        List<Rules> allRules = LitePal.findAll(Rules.class);

        System.out.println(allRules);

    }
}
