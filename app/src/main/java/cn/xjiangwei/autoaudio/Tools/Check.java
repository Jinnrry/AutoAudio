package cn.xjiangwei.autoaudio.Tools;


import org.litepal.LitePal;

import java.util.Arrays;
import java.util.Calendar;

import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;

public class Check {





    public static int[] checkNow() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) week = 7;

        int[] tmpStatus = Status.getStatus();

        System.out.println("临时状态：" + Arrays.toString(tmpStatus));


        int[] ruleStatus = Rules.checkStatus(year, month, day, hour, minute, week);

        if (tmpStatus!=null)
            return tmpStatus;

        return ruleStatus;
    }

}
