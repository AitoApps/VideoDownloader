package me.zheteng.android.videosaver.parser.provider;

import me.zheteng.android.videosaver.parser.UrlProvider;

/**
 * 只是存储原始地址
 */
public class OriginalUrlProvider implements UrlProvider {
    private String mTargetUrl;

    public OriginalUrlProvider(String original) {
        mTargetUrl = original;
    }

    @Override
    public String getTargetUrl() {
        return mTargetUrl;
    }
}
