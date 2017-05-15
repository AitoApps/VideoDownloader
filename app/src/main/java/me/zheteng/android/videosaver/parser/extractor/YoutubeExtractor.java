package me.zheteng.android.videosaver.parser.extractor;

import android.support.annotation.NonNull;
import me.zheteng.android.videosaver.parser.Extractor;
import me.zheteng.android.videosaver.parser.Video;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * YouTube
 */
public class YoutubeExtractor implements Extractor {
    private static Map<String, String> sQualities = new HashMap<>();

    static {
        sQualities.put("13", "3GP Low Quality - 176x144");
        sQualities.put("17", "3GP Medium Quality - 176x144");
        sQualities.put("36", "3GP High Quality - 320x240");
        sQualities.put("5", "FLV Low Quality - 400x226");
        sQualities.put("6", "FLV Medium Quality - 640x360");
        sQualities.put("34", "FLV Medium Quality - 640x360");
        sQualities.put("35", "FLV High Quality - 854x480");
        sQualities.put("43", "WEBM Low Quality - 640x360");
        sQualities.put("44", "WEBM Medium Quality - 854x480");
        sQualities.put("45", "WEBM High Quality - 1280x720");
        sQualities.put("18", "MP4 Medium Quality - 480x360");
        sQualities.put("22", "MP4 High Quality - 1280x720");
        sQualities.put("37", "MP4 High Quality - 1920x1080");
        sQualities.put("38", "MP4 High Quality - 4096x230");
    }

    @NonNull
    @Override
    public List<Video> extract(String html) {
        Pattern pattern = Pattern.compile("\"url_encoded_fmt_stream_map\":\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        List<Video> videos = new ArrayList<>();
        if (matcher.find()) {
            String result = matcher.group(1);
            String[] strings = result.split(",");
            for (int i = 0; i < strings.length; i++) {
                String cur = strings[i];
                cur = cur.replace("\\u0026", "&");
                String[] params = cur.split("&");

                String url = null;
                String typeid = null;
                String type = null;
                String quality = null;
                for (int j = 0; j < params.length; j++) {
                    if (params[j].startsWith("type")) {
                        type = params[j].substring(5); // video%2Fmp4%3B+codecs%3D%22avc1.64001F%2C+mp4a.40.2%22
                        continue;
                    }
                    if (params[j].startsWith("url")) {
                        url = params[j].substring(4); // url
                        continue;
                    }
                    if (params[j].startsWith("itag")) {
                        typeid = params[j].substring(5); // 22
                        continue;
                    }
                    if (params[j].startsWith("quality")) {
                        quality = params[j].substring(8); // hd720
                    }
                }

                try {
                    url = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Video video = new Video(url);
                video.cover = "";
                video.desc = sQualities.get(typeid);
                video.format = sQualities.get(typeid);

                videos.add(video);
            }
        }

        return videos;
    }
}
