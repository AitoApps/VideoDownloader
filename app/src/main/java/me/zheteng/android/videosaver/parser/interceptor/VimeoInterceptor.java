package me.zheteng.android.videosaver.parser.interceptor;

import android.support.annotation.NonNull;
import me.zheteng.android.videosaver.parser.RequestInterceptor;
import okhttp3.Request;

/**
 * Vimeo 需要加个Header
 */
public class VimeoInterceptor implements RequestInterceptor {

    private String mId;

    public VimeoInterceptor(String id) {
        mId = id;
    }

    @NonNull
    @Override
    public Request.Builder intercept(Request.Builder builder) {
        return builder
                .header("Content-Type", "application/json")
                .header("Referer", "https://vimeo.com/" + mId);
    }
}
