package cn.xjiangwei.autoaudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.litepal.LitePal;

import java.util.Arrays;
import java.util.List;

import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.initialize(this);


    }


    public void test(View view) {
        List<Rules> allRules = LitePal.findAll(Rules.class);
        System.out.println(allRules);
    }

    public void test2(View view) {
        int[] res = Rules.checkStatus(2018, 12, 28, 21, 40);
        System.out.println(Arrays.toString(res));
    }

    public void test3(View view) {
        Rules.addRules(0, 0, 28, 0, 0, Rules.OPEN, Rules.OPEN, Rules.OPEN);
    }

    public void test4(View view) {
        int[] res= Status.getStatus();
        System.out.println(Arrays.toString(res));
    }
}
