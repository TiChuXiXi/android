package com.example.mediaplayer;

import java.io.Serializable;

public class Music implements Serializable {

    public static final String MusicName = "MUSICNAME";
    public static final String MusicId = "MUSICID";
    public static final String SingerName = "SINGERNAME";

    private String musicName;
    private String singerName;
    private int id;
    private boolean isAdd;

    public Music(String musicName, String singerName, int id, boolean isAdd){
        this.musicName = musicName;
        this.singerName = singerName;
        this.id = id;
        this.isAdd = isAdd;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
}
