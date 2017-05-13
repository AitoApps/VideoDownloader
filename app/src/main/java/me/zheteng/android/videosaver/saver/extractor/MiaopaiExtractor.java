package me.zheteng.android.videosaver.saver.extractor;

import me.zheteng.android.videosaver.saver.Extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 秒拍抽取
 */
public class MiaopaiExtractor implements Extractor {
    @Override
    public String extract(String html) {
        Pattern pattern = Pattern.compile("\"videoSrc\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
