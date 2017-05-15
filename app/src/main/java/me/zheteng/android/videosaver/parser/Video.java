package me.zheteng.android.videosaver.parser;

import android.support.annotation.NonNull;

/**
 * 解析出来的Video对象
 */
public class Video {
    public String format;
    public String desc;
    public String cover;
    public final String url;

    public Video(@NonNull String url) {
        this.url = url;
    }
}