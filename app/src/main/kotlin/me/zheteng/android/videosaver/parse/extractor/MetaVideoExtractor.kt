
package me.zheteng.android.videosaver.parse.extractor

import me.zheteng.android.videosaver.parse.Extractor
import me.zheteng.android.videosaver.parse.ParserUtils
import me.zheteng.android.videosaver.parse.Video
import java.util.*
import java.util.regex.Pattern


/**
 * 从Meta信息里抽取视频地址
 */
class MetaVideoExtractor : Extractor {
    override fun extract(html: String): List<Video> {
        val pattern = Pattern.compile("<meta([^>]+property=\"([^\"]*)\"[^>]+content=\"([^\"]*)\"|" + "[^>]+content=\"([^\"]*)\"[^>]+property=\"([^\"]*)\")[^>]*>",
                Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(html)
        val hits = HashMap<String, String>()
        while (matcher.find()) {
            if (matcher.group(2) != null) {
                hits.put(matcher.group(2), matcher.group(3))
            } else if (matcher.group(5) != null) {
                hits.put(matcher.group(5), matcher.group(4))
            }
        }

        var videoUrl: String? = findVideoUrl(hits, "og:video:url")
        if (ParserUtils.isEmpty(videoUrl)) {
            videoUrl = findVideoUrl(hits, "og:video")
        }
        if (ParserUtils.isEmpty(videoUrl)) {
            videoUrl = findVideoUrl(hits, "og:video:secure_url")
        }

        val list = ArrayList<Video>()
        if (!ParserUtils.isEmpty(videoUrl)) {
            list.add(Video(videoUrl!!))
        }

        return list

    }

    private fun findVideoUrl(hits: Map<String, String>, key: String): String? {
        var videoUrl: String? = null
        for ((key1, value) in hits) {
            if (key1.equals(key, ignoreCase = true)) {
                videoUrl = value
                break
            }
        }
        return videoUrl
    }
}