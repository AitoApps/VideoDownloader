package me.zheteng.android.videosaver.parser;

import android.support.annotation.NonNull;
import okhttp3.Request;

/**
 *
 */
public interface RequestInterceptor {
    @NonNull Request.Builder intercept(Request.Builder builder);
}
