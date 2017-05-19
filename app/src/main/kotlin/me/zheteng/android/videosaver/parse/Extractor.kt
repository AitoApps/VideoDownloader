package me.zheteng.android.videosaver.parse

/**
 * 从HTML里抽取需要的信息
 */
interface Extractor {
    /**
     * 从html代码里抽取出需要的信息

     * @param html 被抽取的HTML字符串
     * *
     * @return 抽取出来的信息，如果没有就返回size为0的列表，不空
     */
    fun extract(html: String): List<Video>
}