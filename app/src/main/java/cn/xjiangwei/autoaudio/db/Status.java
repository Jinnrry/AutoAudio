package cn.xjiangwei.autoaudio.db;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class Status extends LitePalSupport {


    private long id;
    private long end_time;
    private int audio_status;
    private int ring_status;
    private int clock_status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getAudio_status() {
        return audio_status;
    }

    public void setAudio_status(int audio_status) {
        this.audio_status = audio_status;
    }

    public int getRing_status() {
        return ring_status;
    }

    public void setRing_status(int ring_status) {
        this.ring_status = ring_status;
    }

    public int getClock_status() {
        return clock_status;
    }

    public void setClock_status(int clock_status) {
        this.clock_status = clock_status;
    }


    public static void add(int audio_status, int ring_status, int clock_status, int time) {
        Status firststatus = LitePal.findFirst(Status.class);
        if (firststatus != null) firststatus.delete();
        firststatus = new Status();
        firststatus.setAudio_status(audio_status);
        firststatus.setRing_status(ring_status);
        firststatus.setClock_status(clock_status);
        long timeStamp = System.currentTimeMillis();
        timeStamp += time * 1000 * 60;
        firststatus.setEnd_time(timeStamp);
        firststatus.save();
        System.out.println(firststatus);
    }

    public static int getEndTime()
    {
        Status status = null;
        status = LitePal.findFirst(Status.class);
        long timeStamp = System.currentTimeMillis();
        if (status == null) {
            return 0;
        }
        if (status.getEnd_time() < timeStamp) {
            status.delete();
            return 0;
        }
        return (int) ((status.getEnd_time()-timeStamp)/1000/60);
    }

    public static int[] getStatus() {
        Status status = null;
        status = LitePal.findFirst(Status.class);
        long timeStamp = System.currentTimeMillis();
        if (status == null) {
            return null;
        }
        if (status.end_time < timeStamp) {
            status.delete();
            return null;
        }
        return new int[]{status.getAudio_status(), status.getRing_status(), status.getClock_status()};
    }


    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", end_time=" + end_time +
                ", audio_status=" + audio_status +
                ", ring_status=" + ring_status +
                ", clock_status=" + clock_status +
                '}';
    }
}
