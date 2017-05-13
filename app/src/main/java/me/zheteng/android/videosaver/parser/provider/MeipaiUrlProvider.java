package me.zheteng.android.videosaver.parser.provider;

import me.zheteng.android.videosaver.parser.UrlProvider;

/**
 * 美拍URL拼接
 */
public class MeipaiUrlProvider implements UrlProvider {
    public static final String BASE_URL = "http://www.meipai.com/media/";
    private final String mId;

    public MeipaiUrlProvider(String id) {
        mId = id;
    }

    /**
     * 页面id：http://www.meipai.com/media/{id}
     * http://www.meipai.com/media/416832257
     */
    @Override
    public String getTargetUrl() {
        return BASE_URL + mId;
    }
}
