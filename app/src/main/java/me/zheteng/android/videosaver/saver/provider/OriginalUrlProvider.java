package me.zheteng.android.videosaver.saver.provider;

import me.zheteng.android.videosaver.saver.UrlProvider;

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
