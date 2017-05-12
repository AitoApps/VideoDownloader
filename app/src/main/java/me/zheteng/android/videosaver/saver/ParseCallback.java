package me.zheteng.android.videosaver.saver;

/**
 * 解析回调
 */
public interface ParseCallback {
    void onParsed(VideoParser parser, String videoUrl);
    void onFailure(VideoParser parser, Throwable e);
}
