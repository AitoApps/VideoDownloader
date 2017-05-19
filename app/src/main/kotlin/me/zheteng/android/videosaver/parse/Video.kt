package me.zheteng.android.videosaver.parse

/**
 * 解析出来的Video对象
 */
class Video(val url: String) {
    var format: String? = null
    var desc: String? = null
    var cover: String? = null
}