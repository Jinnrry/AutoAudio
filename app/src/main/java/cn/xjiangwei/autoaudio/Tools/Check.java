package cn.xjiangwei.autoaudio.Tools;


import org.litepal.LitePal;

import java.util.Arrays;
import java.util.Calendar;

import cn.xjiangwei.autoaudio.db.Rules;
import cn.xjiangwei.autoaudio.db.Status;

public class Check {


    public static String getNowRule() {
        Status status = null;
        status = LitePal.findFirst(Status.class);
        long timeStamp = System.currentTimeMillis();

        if (status != null) {
            if ( status.getEnd_time() > timeStamp) {
                return ("临时状态：" + status);
            }
            if (status.getEnd_time() < timeStamp) {
                status.delete();
            }

        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) week = 7;
        int[] ruleStatus = Rules.checkStatus(year, month, day, hour, minute, week);

        return "根据规则：媒体音量：" + Rules.STATUS[ruleStatus[0]] + "铃声音量：" + Rules.STATUS[ruleStatus[1]] + "闹钟音量：" + Rules.STATUS[ruleStatus[2]];
    }


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

        if (tmpStatus[0] == Rules.DEFAULT) {
            tmpStatus[0] = ruleStatus[0];
        }

        if (tmpStatus[1] == Rules.DEFAULT) {
            tmpStatus[1] = ruleStatus[1];
        }
        if (tmpStatus[2] == Rules.DEFAULT) {
            tmpStatus[2] = ruleStatus[2];
        }

        return tmpStatus;
    }

}
