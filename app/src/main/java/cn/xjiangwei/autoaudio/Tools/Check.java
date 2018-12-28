package cn.xjiangwei.autoaudio.Tools;


import java.util.Calendar;

import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;

public class Check {


    public static int[] checkNow() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;


        int[] tmpStatus = Status.getStatus();
        int[] ruleStatus = Rules.checkStatus(year, month, day, hour, minute, week);

        return tmpStatus;
    }

}
