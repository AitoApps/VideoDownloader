package me.zheteng.android.videosaver.saver;

/**
 * 快手地址
 */
public class KuaishouUrlProvider implements UrlProvider {
    String mTargetUrl;

    public KuaishouUrlProvider(String original) {
        mTargetUrl = original;
    }

    @Override
    public String getTargetUrl() {
        return mTargetUrl;
    }
}
