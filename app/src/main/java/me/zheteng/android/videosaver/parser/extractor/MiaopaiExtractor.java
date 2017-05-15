package me.zheteng.android.videosaver.parser.extractor;

import android.support.annotation.NonNull;
import me.zheteng.android.videosaver.parser.Extractor;
import me.zheteng.android.videosaver.parser.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 秒拍抽取
 */
public class MiaopaiExtractor implements Extractor {
    @NonNull
    @Override
    public List<Video> extract(String html) {
        Pattern pattern = Pattern.compile("\"videoSrc\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(html);
        List<Video> list = new ArrayList<>();
        if (matcher.find()) {
            list.add(new Video(matcher.group(1)));
        }
        return list;
    }
}
