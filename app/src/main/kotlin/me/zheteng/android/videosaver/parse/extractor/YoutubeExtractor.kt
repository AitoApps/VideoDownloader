package me.zheteng.android.videosaver.parse.extractor

import me.zheteng.android.videosaver.parse.Extractor
import me.zheteng.android.videosaver.parse.Video
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*
import java.util.regex.Pattern


/**
 * YouTube
 */
class YoutubeExtractor : Extractor {

    override fun extract(html: String): List<Video> {
        val pattern = Pattern.compile("\"url_encoded_fmt_stream_map\":\"(.*?)\"", Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(html)
        val videos = ArrayList<Video>()
        if (matcher.find()) {
            val result = matcher.group(1)
            val strings = result.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in strings.indices) {
                var cur = strings[i]
                cur = cur.replace("\\u0026", "&")
                val params = cur.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                var url: String? = null
                var typeid: String? = null
                var type: String? = null
                var quality: String? = null
                for (j in params.indices) {
                    if (params[j].startsWith("type")) {
                        type = params[j].substring(5) // video%2Fmp4%3B+codecs%3D%22avc1.64001F%2C+mp4a.40.2%22
                        continue
                    }
                    if (params[j].startsWith("url")) {
                        url = params[j].substring(4) // url
                        continue
                    }
                    if (params[j].startsWith("itag")) {
                        typeid = params[j].substring(5) // 22
                        continue
                    }
                    if (params[j].startsWith("quality")) {
                        quality = params[j].substring(8) // hd720
                    }
                }

                try {
                    url = URLDecoder.decode(url, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                val video = Video(url!!)
                video.cover = ""
                video.desc = sQualities[typeid]
                video.format = sQualities[typeid]

                videos.add(video)
            }
        }

        return videos
    }

    companion object {
        private val sQualities = HashMap<String, String>()

        init {
            sQualities.put("13", "3GP Low Quality - 176x144")
            sQualities.put("17", "3GP Medium Quality - 176x144")
            sQualities.put("36", "3GP High Quality - 320x240")
            sQualities.put("5", "FLV Low Quality - 400x226")
            sQualities.put("6", "FLV Medium Quality - 640x360")
            sQualities.put("34", "FLV Medium Quality - 640x360")
            sQualities.put("35", "FLV High Quality - 854x480")
            sQualities.put("43", "WEBM Low Quality - 640x360")
            sQualities.put("44", "WEBM Medium Quality - 854x480")
            sQualities.put("45", "WEBM High Quality - 1280x720")
            sQualities.put("18", "MP4 Medium Quality - 480x360")
            sQualities.put("22", "MP4 High Quality - 1280x720")
            sQualities.put("37", "MP4 High Quality - 1920x1080")
            sQualities.put("38", "MP4 High Quality - 4096x230")
        }
    }
}
