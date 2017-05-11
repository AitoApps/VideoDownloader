package me.zheteng.android.videosaver.saver;

/**
 * 解析回调
 */
public interface ParseCallback {
    void onParsed(SimpleParser parser, String videoUrl);
    void onFailure(SimpleParser parser, Exception e);
}
