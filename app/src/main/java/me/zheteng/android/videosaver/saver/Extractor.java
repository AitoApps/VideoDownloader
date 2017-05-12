package me.zheteng.android.videosaver.saver;

/**
 * 从HTML里抽取需要的信息
 */
public interface Extractor {
    /**
     * 从html代码里抽取出需要的信息
     *
     * @param html 被抽取的HTML字符串
     * @return 抽取出来的信息
     */
    String extract(String html);
}
