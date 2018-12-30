package cn.xjiangwei.autoaudio.db;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import cn.xjiangwei.autoaudio.vo.Item;

public class Rules extends LitePalSupport {

    public static final int OPEN = 2;
    public static final int CLOSE = 1;
    public static final int DEFAULT = 0;
    private int id;

    @Column(nullable = true)
    private int year;
    @Column(nullable = true)
    private int month;
    @Column(nullable = true)
    private int day;
    @Column(nullable = true)
    private int hour;
    @Column(nullable = true)
    private int min;
    @Column(nullable = true)
    private int week;

    private int audio;
    private int ring;
    private int clock;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }


    public static boolean addRules(int year, int month, int day, int hour, int min, int week, int audio, int ring, int clock) {
        Rules rules = new Rules();
        if (year > 0) rules.setYear(year);
        if (month > 0) rules.setMonth(month);
        if (day > 0) rules.setDay(day);
        if (hour > 0) rules.setHour(hour);
        if (min > 0) rules.setMin(min);
        if (audio > 0) rules.setAudio(audio);
        if (ring > 0) rules.setRing(audio);
        if (clock > 0) rules.setClock(clock);
        if (week > 0) rules.setWeek(week);
        return rules.save();
    }


    public static int[] checkStatus(int year, int month, int day, int hour, int min, int week) {
        int ring = 0;
        int audio = 0;
        int clock = 0;

        List<Rules> rulesList = LitePal
                .where("year = ? or year =0 ", String.valueOf(year))
                .where("month = ? or month = 0", String.valueOf(month))
                .where("day = ? or day = 0", String.valueOf(day))
                .where("hour = ? or hour = 0", String.valueOf(hour))
                .where("min = ? or min = 0", String.valueOf(min))
                .where("week = ? or week = 0", String.valueOf(week))
                .find(Rules.class);
        for (Rules rule : rulesList) {
            if (rule.getAudio() == Rules.OPEN && audio == 0) {
                audio = Rules.OPEN;
            } else if (rule.getAudio() == Rules.CLOSE && audio == 0) {
                audio = Rules.CLOSE;
            }
            if (rule.getRing() == Rules.OPEN && ring == 0) {
                ring = Rules.OPEN;
            } else if (rule.getRing() == Rules.CLOSE && ring == 0) {
                ring = Rules.CLOSE;
            }
            if (rule.getClock() == Rules.OPEN && clock == 0) {
                clock = Rules.OPEN;
            } else if (rule.getClock() == Rules.CLOSE && clock == 0) {
                clock = Rules.CLOSE;
            }
        }
        return new int[]{audio, ring, clock};
    }

    public static ArrayList<Item> getList() {

        ArrayList<Item> list = new ArrayList<Item>();
        List<Rules> rulesList = LitePal.findAll(Rules.class);
        System.out.println(rulesList);
        for (Rules rules : rulesList) {
            String str_rules = "";
            String Values = "";
            switch (rules.getAudio()) {
                case Rules.CLOSE:
                    if (Values.length() > 0) Values += " ";
                    Values += "媒体音量：关闭";
                    break;
                case Rules.DEFAULT:
                    break;
                case Rules.OPEN:
                    if (Values.length() > 0) Values += " ";
                    Values += "媒体音量：开";
                    break;
            }
            switch (rules.getClock()) {
                case Rules.CLOSE:
                    if (Values.length() > 0) Values += " ";
                    Values += "闹钟音量：关闭";
                    break;
                case Rules.DEFAULT:
                    break;
                case Rules.OPEN:
                    if (Values.length() > 0) Values += " ";
                    Values += "闹钟音量：开";
                    break;
            }

            switch (rules.getRing()) {
                case Rules.CLOSE:
                    if (Values.length() > 0) Values += " ";
                    Values += "来电音量：关闭";
                    break;
                case Rules.DEFAULT:
                    break;
                case Rules.OPEN:
                    if (Values.length() > 0) Values += " ";
                    Values += "来电音量：开";
                    break;
            }
            if (rules.getYear() > 0) {
                if (str_rules.length() > 0) str_rules += "且";
                str_rules += rules.getYear() + "年";
            }
            if (rules.getMonth() > 0) {
                if (str_rules.length() > 0) str_rules += "且";

                str_rules += rules.getMonth() + "月";
            }
            if (rules.getWeek() > 0) {
                if (str_rules.length() > 0) str_rules += "且";

                str_rules += "周" + rules.getWeek();
            }
            if (rules.getDay() > 0) {
                if (str_rules.length() > 0) str_rules += "且";

                str_rules += rules.getDay() + "日";
            }

            if (rules.getHour() > 0) {
                if (str_rules.length() > 0) str_rules += "且";

                str_rules += rules.getHour() + "时";
            }

            if (rules.getMin() > 0) {
                if (str_rules.length() > 0) str_rules += "且";

                str_rules += rules.getMin() + "分";
            }

            list.add(new Item(rules.getId(), str_rules, Values));


        }


        return list;
    }


    @Override
    public String toString() {
        return "Rules{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", min=" + min +
                ", week=" + week +
                ", audio=" + audio +
                ", ring=" + ring +
                ", clock=" + clock +
                '}';
    }
}
