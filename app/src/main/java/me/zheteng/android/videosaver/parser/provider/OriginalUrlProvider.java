package me.zheteng.android.videosaver.parser.provider;

import me.zheteng.android.videosaver.parser.UrlProvider;

/**
 * 快手地址
 */
public class OriginalUrlProvider implements UrlProvider {
    String mTargetUrl;

    public OriginalUrlProvider(String original) {
        mTargetUrl = original;
    }

    @Override
    public String getTargetUrl() {
        return mTargetUrl;
    }
}
