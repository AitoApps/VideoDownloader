package me.zheteng.android.videosaver.parse

/**
 * 解析回调
 */
interface ParseCallback {
    fun onParsed(parser: VideoParser, videos: List<Video>)
    fun onFailure(parser: VideoParser, e: Throwable)
}
