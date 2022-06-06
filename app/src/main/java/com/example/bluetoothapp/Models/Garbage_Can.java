package com.example.bluetoothapp.Models;

public class Garbage_Can {
    private String ip;
    private boolean door, mode;//door 1 la dang tai che, door 0 la k phai tai che
    private float volume_recycle, volume_nonRecycle, weight_difference;
    //true la Mo, false la Dong


    public Garbage_Can(String ip, boolean mode, float volume_recycle, float volume_nonRecycle, float weight_difference) {
        this.ip = ip;
        this.door = door;
        this.mode = mode;
        this.volume_recycle = volume_recycle;
        this.volume_nonRecycle = volume_nonRecycle;
        this.weight_difference = weight_difference;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isDoor() {
        return door;
    }

    public void setDoor(boolean door) {
        this.door = door;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
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

    @Override
    public String toString() {
        return "Garbage_Can{" +
                "ip='" + ip + '\'' +
                ", door=" + door +
                ", mode=" + mode +
                ", volume_recycle=" + volume_recycle +
                ", volume_nonRecycle=" + volume_nonRecycle +
                ", weight_difference=" + weight_difference +
                '}';
    }
}
