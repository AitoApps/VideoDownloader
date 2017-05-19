package me.zheteng.android.videosaver.parse.extractor

import me.zheteng.android.videosaver.parse.Extractor
import me.zheteng.android.videosaver.parse.Video
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 * Vimeo 视频抽取
 * acknowledgement:https://github.com/ed-george/AndroidVimeoExtractor
 * /blob/master/library/src/main/java/uk/breedrapps/vimeoextractor/VimeoVideo.java
 */
class VimeoExtractor : Extractor {
    override fun extract(json: String): List<Video> {
        val list = ArrayList<Video>()
        try {
            //Turn JSON string to object
            val requestJson = JSONObject(json)

            //Access video information
            val videoInfo = requestJson.getJSONObject("video")
            val title = videoInfo.getString("title")

            //Get thumbnail information
            val thumbsInfo = videoInfo.getJSONObject("thumbs")
            var thumb: String? = null
            val iterator: Iterator<String>
            iterator = thumbsInfo.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                thumb = thumbsInfo.getString(key)
                break
            }

            //Access video stream information
            val streamArray = requestJson.getJSONObject("request")
                    .getJSONObject("files")
                    .getJSONArray("progressive")

            //Get info for each stream available
            for (streamIndex in 0..streamArray.length() - 1) {
                val stream = streamArray.getJSONObject(streamIndex)
                val url = stream.getString("url")
                val quality = stream.getString("quality")
                //Store stream information
                val video = Video(url)
                video.format = quality
                video.desc = title
                video.cover = thumb
                list.add(video)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }
}
