package me.zheteng.android.videosaver.parse.extractor

import me.zheteng.android.videosaver.parse.Extractor
import me.zheteng.android.videosaver.parse.Video
import java.util.*
import java.util.regex.Pattern


/**
 * 秒拍抽取
 */
class MiaopaiExtractor : Extractor {
    override fun extract(html: String): List<Video> {
        val pattern = Pattern.compile("\"videoSrc\":\"([^\"]+)\"")
        val matcher = pattern.matcher(html)
        val list = ArrayList<Video>()
        if (matcher.find()) {
            list.add(Video(matcher.group(1)))
        }
        return list
    }
}
