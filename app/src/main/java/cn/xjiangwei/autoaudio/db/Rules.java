package cn.xjiangwei.autoaudio.db;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import cn.xjiangwei.autoaudio.vo.Item;

public class Rules extends LitePalSupport {


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
    @Column(nullable = true)
    private int end_hour;
    @Column(nullable = true)
    private int end_min;

    private int audio;
    private int ring;
    private int clock;

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_min() {
        return end_min;
    }

    public void setEnd_min(int end_min) {
        this.end_min = end_min;
    }

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


    public static boolean addRules(int year, int month, int day, int hour, int min, int end_hour, int end_min, int week, int audio, int ring, int clock) {
        Rules rules = new Rules();
        if (year > 0) rules.setYear(year);
        if (month > 0) rules.setMonth(month);
        if (day > 0) rules.setDay(day);
        rules.setHour(hour);
        rules.setMin(min);
        rules.setAudio(audio);
        rules.setRing(ring);
        rules.setClock(clock);
        if (week > 0) rules.setWeek(week);
        rules.setEnd_hour(end_hour);
        rules.setEnd_min(end_min);
        return rules.save();
    }


    public static int[] checkStatus(int year, int month, int day, int hour, int min, int week) {
        int ring = -1;
        int audio = -1;
        int clock = -1;

        System.out.println("year = " + year);
        System.out.println("month = " + month);
        System.out.println("day = " + day);
        System.out.println("hour = " + hour);
        System.out.println("min = " + min);
        System.out.println("week = " + week);
        List<Rules> rulesList = LitePal
                .where("(year = ? or year <=0) " +
                                "and (month = ? or month <= 0) " +
                                "and (day = ? or day <= 0) " +
                                "and ((hour <= ? and end_hour >=?) or (hour = -1 and end_hour = -1)) and " +
                                "((min <= ? and end_min >=?) or (min = -1 and end_min = -1)) and " +
                                "(week = ? or week <= 0)",
                        String.valueOf(year),
                        String.valueOf(month),
                        String.valueOf(day),
                        String.valueOf(hour),
                        String.valueOf(hour),
                        String.valueOf(min),
                        String.valueOf(min),
                        String.valueOf(week)
                ).find(Rules.class);
        System.out.println("当前生效规则：");
        System.out.println(rulesList);
        for (Rules rule : rulesList) {
            if (rule.getAudio() > audio) audio = rule.getAudio();
            if (rule.getRing() > ring) ring = rule.getRing();
            if (rule.getClock() > clock) clock = rule.getClock();
        }
        return new int[]{audio, ring, clock};
    }

    public static ArrayList<Item> getList(int audio_max, int ring_max, int clock_max) {
        ArrayList<Item> list = new ArrayList<Item>();
        List<Rules> rulesList = LitePal.findAll(Rules.class);
        System.out.println(rulesList);
        for (Rules rules : rulesList) {
            String str_rules = "";
            String Values = "";
            if (rules.getAudio() >= 0) {
                Values += "媒体音量：" + String.valueOf((int) (((float) rules.getAudio()) / audio_max * 100)) + "%";
            }
            if (rules.getRing() >= 0) {
                Values += "铃声音量：" + String.valueOf((int) (((float) rules.getRing()) / ring_max * 100)) + "%";
            }
            if (rules.getClock() >= 0) {
                Values += "闹钟音量：" + String.valueOf((int) (((float) rules.getClock()) / clock_max * 100)) + "%";
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

            if (rules.getHour() >= 0) {
                if (str_rules.length() > 0) str_rules += "且";
                if (rules.getEnd_hour() >= 0) {
                    str_rules += rules.getHour() + "至";
                }
                str_rules += rules.getEnd_hour() + "时";
            }

            if (rules.getMin() > 0) {
                if (str_rules.length() > 0) str_rules += "且";
                if (rules.getEnd_hour() >= 0) {
                    str_rules += rules.getMin() + "至";
                }
                str_rules += rules.getEnd_min() + "分";
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
                ", end_hour=" + end_hour +
                ", end_min=" + end_min +
                ", audio=" + audio +
                ", ring=" + ring +
                ", clock=" + clock +
                '}';
    }
}
