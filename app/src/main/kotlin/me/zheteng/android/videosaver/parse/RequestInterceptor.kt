package me.zheteng.android.videosaver.parse

import okhttp3.Request

/**

 */
interface RequestInterceptor {
    fun intercept(builder: Request.Builder): Request.Builder
}