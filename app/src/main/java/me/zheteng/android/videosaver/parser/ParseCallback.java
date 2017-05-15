package me.zheteng.android.videosaver.parser;

import java.util.List;

/**
 * 解析回调
 */
public interface ParseCallback {
    void onParsed(VideoParser parser, List<Video> videos);
    void onFailure(VideoParser parser, Throwable e);
}
