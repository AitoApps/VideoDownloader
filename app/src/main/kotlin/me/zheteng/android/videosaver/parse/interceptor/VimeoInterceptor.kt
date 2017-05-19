package me.zheteng.android.videosaver.parse.interceptor

import me.zheteng.android.videosaver.parse.RequestInterceptor
import okhttp3.Request

/**
 * Vimeo 需要加个Header
 */
class VimeoInterceptor(private val mId: String) : RequestInterceptor {

    override fun intercept(builder: Request.Builder): Request.Builder {
        return builder
                .header("Content-Type", "application/json")
                .header("Referer", "https://vimeo.com/" + mId)
    }
}
