package com.example.bluetoothapp.Models;

public class Garbage_Can {
    private String ip;
    private boolean mode;//door 1 la dang la tu dong, door 0 la thu cong
    private int thread;
    private float volume_recycle, volume_nonRecycle, weight_difference, value;
    //true la Mo, false la Dong


    public Garbage_Can(String ip, boolean mode, int thread, float volume_recycle, float volume_nonRecycle, float weight_difference, float value) {
        this.ip = ip;
        this.mode = mode;
        this.thread = thread;
        this.volume_recycle = volume_recycle;
        this.volume_nonRecycle = volume_nonRecycle;
        this.weight_difference = weight_difference;
        this.value = value;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public float getVolume_recycle() {
        return volume_recycle;
    }

    public void setVolume_recycle(float volume_recycle) {
        this.volume_recycle = volume_recycle;
    }

    public float getVolume_nonRecycle() {
        return volume_nonRecycle;
    }

    public void setVolume_nonRecycle(float volume_nonRecycle) {
        this.volume_nonRecycle = volume_nonRecycle;
    }

    public float getWeight_difference() {
        return weight_difference;
    }

    public void setWeight_difference(float weight_difference) {
        this.weight_difference = weight_difference;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Garbage_Can{" +
                "ip='" + ip + '\'' +
                ", mode=" + mode +
                ", thread=" + thread +
                ", volume_recycle=" + volume_recycle +
                ", volume_nonRecycle=" + volume_nonRecycle +
                ", weight_difference=" + weight_difference +
                ", value=" + value +
                '}';
    }
}
