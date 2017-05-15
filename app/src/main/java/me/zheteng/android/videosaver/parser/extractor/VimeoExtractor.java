package me.zheteng.android.videosaver.parser.extractor;

import android.support.annotation.NonNull;
import me.zheteng.android.videosaver.parser.Extractor;
import me.zheteng.android.videosaver.parser.Video;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Vimeo 视频抽取
 * acknowledgement:https://github.com/ed-george/AndroidVimeoExtractor
 * /blob/master/library/src/main/java/uk/breedrapps/vimeoextractor/VimeoVideo.java
 */
public class VimeoExtractor implements Extractor{
    @NonNull
    @Override
    public List<Video> extract(String json) {
        List<Video> list = new ArrayList<>();
        try {
            //Turn JSON string to object
            JSONObject requestJson = new JSONObject(json);

            //Access video information
            JSONObject videoInfo = requestJson.getJSONObject("video");
            String title = videoInfo.getString("title");

            //Get thumbnail information
            JSONObject thumbsInfo = videoInfo.getJSONObject("thumbs");
            String thumb = null;
            Iterator<String> iterator;
            for(iterator = thumbsInfo.keys(); iterator.hasNext();) {
                String key = iterator.next();
                thumb = thumbsInfo.getString(key);
                break;
            }

            //Access video stream information
            JSONArray streamArray = requestJson.getJSONObject("request")
                    .getJSONObject("files")
                    .getJSONArray("progressive");

            //Get info for each stream available
            for (int streamIndex = 0; streamIndex < streamArray.length(); streamIndex++) {
                JSONObject stream = streamArray.getJSONObject(streamIndex);
                String url = stream.getString("url");
                String quality = stream.getString("quality");
                //Store stream information
                Video video = new Video(url);
                video.format = quality;
                video.desc = title;
                video.cover = thumb;
                list.add(video);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
