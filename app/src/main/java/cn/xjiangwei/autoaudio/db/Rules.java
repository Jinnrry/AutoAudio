package cn.xjiangwei.autoaudio.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

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
    private int status;


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", status=" + status +
                '}';
    }
}
