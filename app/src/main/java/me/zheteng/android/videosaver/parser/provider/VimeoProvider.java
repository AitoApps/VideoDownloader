package me.zheteng.android.videosaver.parser.provider;

import me.zheteng.android.videosaver.parser.UrlProvider;

/**
 *
 */
public class VimeoProvider implements UrlProvider{
    private static final String VIMEO_CONFIG_URL = "https://player.vimeo.com/video/";
    private String mId;

    public VimeoProvider(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    @Override
    public String getTargetUrl() {
        return VIMEO_CONFIG_URL + mId + "/config";
    }
}
